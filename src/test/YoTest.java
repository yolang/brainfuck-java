package test;

import java.io.File;

import org.faabtech.brainfuck.impl.YoEngine;

public class YoTest {

	public static void main(String[] args) throws Exception {
		new YoEngine(30000).interpret(new File(
				"samples/yo.yo"));
	}

}
