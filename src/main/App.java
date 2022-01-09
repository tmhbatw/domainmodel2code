/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package main;

import java.io.File;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;

public class App {
	
	//执行xml模型到代码模板的转换过程
	public static void main(String[] args) throws Exception {
		
		System.out.print("0");
		
		// Parse main.egx
		EgxModule module = new EgxModule(new EglFileGeneratingTemplateFactory());
		module.parse(new File("main.egx").getAbsoluteFile());
		
		if (!module.getParseProblems().isEmpty()) {
			System.out.println("Syntax errors found. Exiting.");
			return;
		}
		
		// Load the XML document
		PlainXmlModel model = new PlainXmlModel();
		model.setFile(new File("target.xml"));
		model.setName("L");
		model.load();
		
		// Make the document visible to the EGX program
		module.getContext().getModelRepository().addModel(model);
		// ... and execute
		module.execute();
	}
	
}
