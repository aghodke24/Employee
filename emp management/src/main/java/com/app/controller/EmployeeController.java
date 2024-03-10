package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SignupRequest;
import com.app.entities.Employee;
import com.app.service.EmployeeService;

@RestController // =@Controller : cls level + @ResponseBody : ret type of the req handling
				// methods : @RequestMapping / @GetMapping....
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	// dep
	@Autowired
	private EmployeeService empService;

	public EmployeeController() {
		System.out.println("in def ctor of " + getClass());
	}

	// http://host:port/employees , method=GET
	@GetMapping
	// SC adds @ResponseBody : on ret type
	public List<Employee> fetchAllEmps() {
		System.out.println("in fetch all emps");
		return empService.getAllEmps();
	}

	/*
	 * handler -->@ResponseBody List<Emp>--> D.S SC uses Jackson jars to perform
	 * --ser(marshalling) ---> json[] --> sent to clnt
	 */
	// http://host:port/employees , method=POST
	// add new emp details
	@PostMapping//Unmarshalling
	
	public Employee addNewEmpDetails(@RequestBody  Employee newEmp) {
		System.out.println("in add emp " + newEmp);// newEmp : transient
		return empService.addEmpDetails(newEmp);
	}
	

	// http://host:port/employees/1or 2 or 3..., method = GET
	// get emp details by id
	@GetMapping("/{empId}") // path var or URI template var
	public Employee getEmpDetails(@PathVariable Long empId) {
		System.out.println("in get emp " + empId);
		return empService.getEmpDetailsById(empId);
	}

	// http://host:port/employees/1 or 2 or 3..., method =DELETE
	// delete emp details by id
	@DeleteMapping("/{empId}")//template URI var/path var
	//@PathVariable:anno.to bind incoming path var to the method arg
	//(spring container each and everything)
	public ApiResponse deleteEmpDetails(@PathVariable Long empId) {
		System.out.println("in del emp details " + empId);
		//individual try-catch blocks,no longer required(global exc handler excp)
		try {
			// invoke service layer method
			return new ApiResponse(empService.removeEmpDetails(empId));
		} catch (RuntimeException e) {
			System.out.println("err occurred " + e);
			return new ApiResponse("Server error " + e.getMessage());
		}
	}

	// http://host:port/employees , req body : updated detached entity , method=PUT
	// update emp details
	@PutMapping
	//unmarshalling
	public Employee updateEmpDetails(@RequestBody Employee detachedEmp) {
		System.out.println("in update emp " + detachedEmp);
		// invoke service layer method
		return empService.updateDetails(detachedEmp);
	}

	// http://host:port/employees/signin , method: POST//(security)
	// req body : SigninReq
	// resp : SigninResp
	@PostMapping("/signin")
	public ResponseEntity<?> empLogin(@RequestBody @Valid SigninRequest request) {//unmarshalling
		//server side validation
	
		System.out.println("emp login " + request);
		// invoke service layer
		try {
			return new ResponseEntity<>(empService.authenticate(request),
					HttpStatus.OK);
		} catch (RuntimeException e) {
		System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
		
		@PostMapping("/signup")
		public ResponseEntity<?> empSignup(@RequestBody @Valid SignupRequest request) {//unmarshalling
			//server side validation
		
			System.out.println("emp Signup " + request);
			// invoke service layer
			try {
				return new ResponseEntity<>(empService.authenticate(request),
						HttpStatus.OK);
			} catch (RuntimeException e) {
			System.out.println(e);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
			}//http://localhost:8080/swagger-ui/index.html
	}

}
