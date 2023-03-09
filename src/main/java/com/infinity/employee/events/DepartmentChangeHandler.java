package com.infinity.employee.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.naming.Binding;
import java.util.function.Consumer;

@Component
public class DepartmentChangeHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(DepartmentChangeHandler.class);
    @Bean
    public Consumer<DepartmentChangeModel> departmentConsumer() {
      return departmentChangeModel -> {
          LOGGER.debug("Received a message of type " + departmentChangeModel.type());

          switch(departmentChangeModel.action()){
              case "GET":
                  LOGGER.debug("Received a GET event from the department service for department id {}", departmentChangeModel.departmentId());
                  break;
              case "SAVE":
                  LOGGER.debug("Received a SAVE event from the department service for department id {}", departmentChangeModel.departmentId());
                  break;
              case "UPDATE":
                  LOGGER.debug("Received a UPDATE event from the department service for department id {}", departmentChangeModel.departmentId());
                  break;
              case "DELETE":
                  LOGGER.debug("Received a DELETE event from the department service for department id {}", departmentChangeModel.departmentId());
                  break;
              default:
                  LOGGER.error("Received an UNKNOWN event from the department service of type {}", departmentChangeModel.type());
                  break;
          }          
      }  ;
    }
}
