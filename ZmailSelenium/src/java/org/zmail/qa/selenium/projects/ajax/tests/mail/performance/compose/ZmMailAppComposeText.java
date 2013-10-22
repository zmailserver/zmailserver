package org.zmail.qa.selenium.projects.ajax.tests.mail.performance.compose;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.performance.PerfKey;
import org.zmail.qa.selenium.framework.util.performance.PerfMetrics;
import org.zmail.qa.selenium.framework.util.performance.PerfToken;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;

public class ZmMailAppComposeText extends AjaxCommonTest {

	public ZmMailAppComposeText() {
		logger.info("New " + ZmMailAppComposeText.class.getCanonicalName());

		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 7525760124523255182L;
			{
				put("zmailPrefComposeFormat", "text");
			}
		};

	}

	@Test(description = "Measure the time to load the text compose  window", groups = { "performance" })
	public void ZmMailAppComposeText_01() throws HarnessException {

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailAppCompose,"Load the compose window in text view");

		// Click the new button
		app.zPageMail.zToolbarPressButton(Button.B_NEW);

		PerfMetrics.waitTimestamp(token);

	}
}
