package com.tarena.action.report;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ReportAction {

	public String load() {
		return "success";
	}

}
