package org.zmail.qa.selenium.projects.ajax.tests.mail.performance.compose;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.performance.PerfKey;
import org.zmail.qa.selenium.framework.util.performance.PerfMetrics;
import org.zmail.qa.selenium.framework.util.performance.PerfToken;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;

public class ZmMailAppComposeHtml extends AjaxCommonTest {

	public ZmMailAppComposeHtml() {
		logger.info("New " + ZmMailAppComposeHtml.class.getCanonicalName());

		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 7525760124523255182L;
			{
				put("zmailPrefComposeFormat", "html");
			}
		};

	}

	@Test(description = "Measure the time to load the html compose window", groups = { "performance" })
	public void ZmMailAppComposeHtml_01() throws HarnessException {

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailAppCompose,"Load the compose window in html view");

		// Click the new  button
		app.zPageMail.zToolbarPressButton(Button.B_NEW);

		PerfMetrics.waitTimestamp(token);

	}
}

