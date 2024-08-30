package org.sid.facturationbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.sid.facturationbackend.dtos.RegieAggentDTO;

import org.sid.facturationbackend.exceptions.CustomerNotFoundException;

import org.sid.facturationbackend.services.EstateAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private EstateAccountService bankAccountService;
    @GetMapping("/regieAggent")
    public List<RegieAggentDTO> customers(){
        return bankAccountService.listRegieAggent();
    }
    @GetMapping("/regieAggent/search")
    public List<RegieAggentDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchRegieAgent("%"+keyword+"%");
    }
    @GetMapping("/regieAggent/{id}")
    public RegieAggentDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getRegie(customerId);
    }
    @PostMapping("/regieAggent")
    public RegieAggentDTO saveCustomer(@RequestBody RegieAggentDTO customerDTO){
        return bankAccountService.saveRegieAgent(customerDTO);
    }
    @PutMapping("/regieAggent/{customerId}")
    public RegieAggentDTO updateCustomer(@PathVariable Long customerId, @RequestBody RegieAggentDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateRegie(customerDTO);
    }
    @DeleteMapping("/regieAggent/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        bankAccountService.deleteRegieAgent(id);
    }
}
