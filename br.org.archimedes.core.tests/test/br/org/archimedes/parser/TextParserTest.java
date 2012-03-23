package br.org.archimedes.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidParameterException;

public class TextParserTest {
	private TextParser tp;
	private String message;
	@Before
	public void setUp() {
		tp = new TextParser();
		message = "1;2";
	}
	
	@Test
	public void testConfirmYes() throws InvalidParameterException {
		Assert.assertEquals(Messages.Text_confirmPoint+" "+message, tp.next(message));
		Assert.assertTrue(tp.getParameter() == null);
		Assert.assertTrue(tp.next(Messages.Text_yes) == null);
		Assert.assertEquals(message, tp.getParameter());
		Assert.assertTrue(tp.isDone());
	}
	
	@Test
	public void testConfirmNo() throws InvalidParameterException {
		Assert.assertEquals(Messages.Text_confirmPoint+" "+message, tp.next(message));
		Assert.assertTrue(tp.getParameter() == null);
		Assert.assertEquals(Messages.Text_iteration, tp.next(Messages.Text_no));
		Assert.assertTrue(tp.getParameter() == null);
		Assert.assertFalse(tp.isDone());
	}
	
	@Test
	public void testMessageIsNotPoint() throws InvalidParameterException {
		Assert.assertTrue(tp.next("fillet") == null);
		Assert.assertEquals("fillet", tp.getParameter());
		Assert.assertTrue(tp.isDone());
	}
}
