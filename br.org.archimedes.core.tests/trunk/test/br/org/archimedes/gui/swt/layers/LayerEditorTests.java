package br.org.archimedes.gui.swt.layers;

import junit.framework.Assert;

import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;

public class LayerEditorTests extends SWTBotGefTestCase{
	@Before
	public void setUp() {
		bot.menu("File").menu("New Drawing").click();
	}
	
	@Test
	public void testLineThicknessChange() throws NoActiveDrawingException {
		bot.menu("Edit").menu("Open layer editor...").click();
		bot.shell("Editor de camadas").activate();
		bot.table().select(0);
		bot.spinnerWithLabel("Espessura").setSelection(7536);
		bot.button("Ok").click();
		Assert.assertEquals(7.536, Utils.getController().getActiveDrawing().getCurrentLayer().getThickness());
	}
	
	@After
	public void tearDown() {
		bot.menu("File").menu("Close").click();
		bot.shell("Save Resource").activate();
		bot.button("No").click();
	}
}
