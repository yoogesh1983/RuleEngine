package com.yoogesh.ruleEngine;

import java.io.IOException;
import java.io.InputStreamReader;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

import com.yoogesh.ruleEngine.model.Product;

public class Run {

	public static void main(String[] args) throws DroolsParserException, IOException {
		Product product = new Product();
		product.setType("diamond");
		
		Run run = new Run();
		run.fireDroolsRuleEngine(product);
		
		System.out.println("The discount for the product " + product.getType() + " is " + product.getDiscount());
	}

	private void fireDroolsRuleEngine(Product product) throws DroolsParserException, IOException {
		
		PackageBuilder packageBuilder = new PackageBuilder();
		packageBuilder.addPackageFromDrl(new InputStreamReader(getClass().getResourceAsStream("Rules.drl")));
		
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(packageBuilder.getPackage());
		
		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		workingMemory.insert(product);
		workingMemory.fireAllRules();
	}

}
