package br.org.archimedes.gui.swt.layers;

import junit.framework.Assert;

import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Utils;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.polyline.Polyline;

/* This tests depends on SWTBot plugin (run as SWTBot Test).
 * http://eclipse.org/swtbot/downloads.php */

public class LayerEditorTests extends SWTBotGefTestCase {
	@Before
	public void setUp() {
		bot.menu("File").menu("New Drawing").click();
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
	}
	
	@Test
	public void testLineThicknessChange() throws NoActiveDrawingException {
		bot.menu("Edit").menu("Open layer editor...").click();
		bot.shell("Editor de camadas").activate();
		bot.spinnerWithLabel("Espessura").setSelection(7536);
    	bot.button("Ok").click();
		Assert.assertEquals(7.536, Utils.getController().getActiveDrawing().getCurrentLayer().getThickness());
	}
	
	@Test
	public void testDoACircleMoveItUndoAndRedo() throws NoActiveDrawingException, InterruptedException, NullArgumentException, InvalidArgumentException
	{
		bot.menu("Create").menu("Circle").click();
		bot.text().setText("0,0;0,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("50,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		
		Circle o = (Circle) Utils.getController().getActiveDrawing().getCurrentLayer().getElements().toArray()[0];
		bot.menu("Transform").menu("Move").click();
		bot.text().setText("100,0;-100,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("-100,0;100,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		
		bot.text().setText("").pressShortcut(Keystrokes.LF);
		bot.text().setText("0;0").pressShortcut(Keystrokes.LF);
		bot.text().setText("100;100").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		
		//verifica se o circulo foi deslocado corretamente
		Assert.assertEquals(o, new Circle(new Point(100.0,100.0), 50.0));
		
		//verifica se o undo funcionou corretamente
		bot.menu("Edit").menu("Undo").click();
		Assert.assertEquals(o, new Circle(new Point(0.0, 0.0), 50.0));
		
    	//verifica se o redo funcionou corretamente
		bot.menu("Edit").menu("Redo").click();
		Assert.assertEquals(o, new Circle(new Point(100.0,100.0), 50.0));
	}
	
	@Test
	public void testDoARectangleSelectAllCopyAndPaste() throws NoActiveDrawingException, InterruptedException, InvalidArgumentException, NullArgumentException 
	{
		bot.menu("Create").menu("Rectangle").click();
		bot.text().setText("0,0;0,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("50,0;50,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		Polyline o = (Polyline) Utils.getController().getActiveDrawing().getCurrentLayer().getElements().toArray()[0];
		Assert.assertEquals(o, new Polyline(new Rectangle(0, 0, 50, 50)));
		
		bot.menu("Edit").menu("Select all").click();		
		bot.menu("Edit").menu("Copy to Clipboard").click();
		bot.menu("File").menu("New Drawing").click();
		bot.menu("Edit").menu("Paste").click();
		Assert.assertEquals(o, new Polyline(new Rectangle(0, 0, 50, 50)));
		Thread.sleep(1000);

		bot.menu("File").menu("Close").click();
		bot.shell("Save Resource").activate();
		bot.button("No").click();
	}
	
	@Test
	public void testErase() throws InterruptedException, NoActiveDrawingException, NullArgumentException, InvalidArgumentException
	{
		bot.menu("Create").menu("Infinite line").click();
		bot.text().setText("0,0;0,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("50,0;50,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		InfiniteLine o = (InfiniteLine) Utils.getController().getActiveDrawing().getCurrentLayer().getElements().toArray()[0];
		Assert.assertEquals(o, new InfiniteLine(new Point(0, 0), new Point(50, 50)));

		bot.menu("Create").menu("Arc").click();
		bot.text().setText("0,0;0,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("50,0;50,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("50,0;0,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		Arc a = (Arc) Utils.getController().getActiveDrawing().getCurrentLayer().getElements().toArray()[1];
		Assert.assertEquals(a, new Arc(new Point(0, 0), new Point(50, 50), new Point(50, 0)));
		
		bot.text().setText("-100,0;100,0").pressShortcut(Keystrokes.LF);
		bot.text().setText("100,0;-100,0").pressShortcut(Keystrokes.LF);
		Thread.sleep(1000);
		
		bot.menu("Edit").menu("Erase").click();
		Thread.sleep(1000);
		
		Assert.assertEquals(1, Utils.getController().getActiveDrawing().getCurrentLayer().getElements().size());
	}
	
	@After
	public void tearDown() {
		bot.menu("File").menu("Close").click();
		bot.shell("Save Resource").activate();
		bot.button("No").click();
	}
}
