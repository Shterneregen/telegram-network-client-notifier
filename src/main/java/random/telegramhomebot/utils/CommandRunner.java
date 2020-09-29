package random.telegramhomebot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommandRunner {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final String UTF_8 = "utf-8";

	public List<String> runCommand(String command) {
		return runCommand(command, UTF_8);
	}

	public List<String> runCommand(String command, String encoding) {
		List<String> result = new ArrayList<>();
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), encoding));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream(), encoding));

			String s;
			while ((s = stdInput.readLine()) != null) {
				result.add(s);
			}

			while ((s = stdError.readLine()) != null) {
				result.add(s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = Collections.singletonList(e.getMessage());
		}
		return result;
	}

	void ping(String ip) {
		runCommand(String.format("ping -c 4 %s", ip));
		log.debug("ping {}", ip);
	}
}