package com.app.processor;

import com.app.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component("empProcessor")
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee item) throws Exception {
        Employee emp = null;
        if (item.getSalary() >= 35000.0) {
            item.setGrossSalary(item.getSalary() + (item.getSalary() * .4));
            item.setNetSalary(item.getGrossSalary() - (item.getGrossSalary() * .2));
            return item;
        }
        return null;
    }
}
