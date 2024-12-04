package com.food.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.food.enums.PaymentMethod;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentTypeValidator implements ConstraintValidator<PaymentTypeValidation, String> {
	@Override
	public boolean isValid(String paymentType, ConstraintValidatorContext constraintValidatorContext) {
		if (paymentType == null)
			return false;
		List<String> paymentTypes = Arrays.stream(PaymentMethod.values()).map(Enum::name) // Convert each enum to its
																							// name
				.collect(Collectors.toList());
		return paymentTypes.contains(paymentType);
	}
}
