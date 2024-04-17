package com.sgic.ppms.controller;

import com.sgic.ppms.dto.OtherChildDTO;
import com.sgic.ppms.entities.OtherChild;
import com.sgic.ppms.services.OtherChildService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.OTHER_CHILD)
public class OtherChildController {

    @Autowired
    private OtherChildService otherChildService;

    @PostMapping(EndpointBundle.CREATE)
    public ResponseEntity<ResponseWrapper<OtherChild>> createOtherChild(@RequestBody OtherChildDTO otherChildDTO) {
        return otherChildService.createOtherChild(otherChildDTO);
    }

    @PutMapping(EndpointBundle.UPDATE_BY_ID)
    public ResponseEntity<ResponseWrapper<OtherChild>> updateOtherChild(@PathVariable Long id,
                                                                        @RequestBody OtherChildDTO otherChildDTO) {
        return otherChildService.updateOtherChild(id, otherChildDTO);
    }

    @GetMapping(EndpointBundle.GET_BY_ID)
    public ResponseEntity<ResponseWrapper<OtherChild>> getOtherChildById(@PathVariable Long id) {
        return otherChildService.getOtherChildById(id);
    }

    @DeleteMapping(EndpointBundle.DELETE_BY_ID)
    public ResponseEntity<ResponseWrapper<String>> deleteOtherChildById(@PathVariable Long id) {
        return otherChildService.deleteOtherChildById(id);
    }

}


