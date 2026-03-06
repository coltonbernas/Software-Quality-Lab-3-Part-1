package com.ontariotechu.sofe3980U;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BinaryController {

    @GetMapping("/")
    public String getCalculator(@RequestParam(name="operand1", required=false, defaultValue="") String operand1, Model model) {
        model.addAttribute("operand1", operand1);
        model.addAttribute("operand1Focused", operand1.length() > 0);
        
        return "calculator";
    }
    
@PostMapping("/")
public String result(@RequestParam(name="operand1", required=false) String operand1,
                     @RequestParam(name="operator", required=false) String operator,
                     @RequestParam(name="operand2", required=false) String operand2,
                     Model model) {

    // 1. Force nulls to empty strings just in case
    String op1 = (operand1 == null) ? "" : operand1;
    String op2 = (operand2 == null) ? "" : operand2;
    String opr = (operator == null) ? "" : operator;

    // 2. Fill the model with these safe strings
    model.addAttribute("operand1", op1);
    model.addAttribute("operator", opr);
    model.addAttribute("operand2", op2);
    model.addAttribute("result", ""); 

    // 3. STRICT CHECK: If any are empty, jump to Error immediately
    // Do NOT call 'new Binary()' if these are empty
    if (op1.isEmpty() || op2.isEmpty() || opr.isEmpty()) {
        return "Error"; 
    }

    try {
        Binary number1 = new Binary(op1);
        Binary number2 = new Binary(op2);
        String resultValue = "";

        switch(opr) {
            case "+":
                resultValue = Binary.add(number1, number2).getValue();
                break;
            case "|":
                resultValue = Binary.or(number1, number2).getValue();
                break;
            case "&":
                resultValue = Binary.and(number1, number2).getValue();
                break;
            case "*":
                resultValue = Binary.multiply(number1, number2).getValue();
                break;
            default:
                return "Error";
        }

        model.addAttribute("result", resultValue);
        return "result";
        
    } catch (Exception e) {
        // This catches any unexpected crashes and directs to Error
        return "Error";
    }
}
}

