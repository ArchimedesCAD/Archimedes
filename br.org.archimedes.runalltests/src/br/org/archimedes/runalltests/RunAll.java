package br.org.archimedes.runalltests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import br.org.archimedes.controller.ActiveStateTest;
import br.org.archimedes.intersectors.ArcArcIntersectorTest;
import br.org.archimedes.io.svg.elements.ArcExporterTest;
import br.org.archimedes.extenders.ArcExtenderTest;
import br.org.archimedes.arc.ArcFactoryTest;
import br.org.archimedes.intersectors.ArcInfiniteLineIntersectorTest;
import br.org.archimedes.intersectors.ArcLineIntersectorTest;
import br.org.archimedes.arc.ArcTest;
import br.org.archimedes.trimmers.ArcTrimTest;
import br.org.archimedes.polyline.area.AreaPerimeterFactoryTest;
import br.org.archimedes.io.svg.elements.CircleExporterTest;
import br.org.archimedes.circle.CircleFactoryTest;
import br.org.archimedes.intersectors.CircleLineIntersectorTest;
import br.org.archimedes.intersectors.CirclePolylineIntersectorTest;
import br.org.archimedes.circle.CircleTest;
import br.org.archimedes.trimmers.CircleTrimTest;
import br.org.archimedes.parser.CommandParserTest;
import br.org.archimedes.copypaste.CopyPasteFactoryTest;
import br.org.archimedes.copytoclipboard.CopyToClipboardFactoryTest;
import br.org.archimedes.fillet.DefaultFilleterTest;
import br.org.archimedes.io.svg.elements.DimensionExporterTest;
import br.org.archimedes.dimension.test.DimensionFactoryTest;
import br.org.archimedes.io.xml.parsers.DimensionParserTest;
import br.org.archimedes.dimension.test.DimensionTest;
import br.org.archimedes.distance.DistanceFactoryTest;
import br.org.archimedes.parser.DistanceParserTest;
import br.org.archimedes.parser.DoubleDecoratorParserTest;
import br.org.archimedes.model.DrawingIntersectionTest;
import br.org.archimedes.model.DrawingTest;
import br.org.archimedes.text.edittext.tests.EditTextCommandTest;
import br.org.archimedes.text.edittext.tests.EditTextFactoryTest;
import br.org.archimedes.erase.EraseFactoryTest;
import br.org.archimedes.polyline.explode.ExplodeFactoryTest;
import br.org.archimedes.extend.ExtendCommandTest;
import br.org.archimedes.extend.ExtendManagerTest;
import br.org.archimedes.controller.commands.ExtendTest;
import br.org.archimedes.fillet.FilletCommandTest;
import br.org.archimedes.fillet.FilletFactoryTest;
import br.org.archimedes.GeometricsTest;
import br.org.archimedes.infiniteLine.HorizontalInfiniteLineTest;
import br.org.archimedes.io.svg.elements.InfiniteLineExporterTest;
import br.org.archimedes.intersectors.InfiniteLinePolylineIntersectorTest;
import br.org.archimedes.trimmers.InfiniteLineTrimTest;
import br.org.archimedes.controller.InputControllerTest;
import br.org.archimedes.io.svg.elements.LeaderExporterTest;
import br.org.archimedes.leader.LeaderTest;
import br.org.archimedes.io.svg.elements.LineExporterTest;
import br.org.archimedes.extenders.LineExtenderTest;
import br.org.archimedes.line.LineFactoryTest;
import br.org.archimedes.intersectors.LineInfiniteLineIntersectorTest;
import br.org.archimedes.intersectors.LineLeaderIntersectorTest;
import br.org.archimedes.intersectors.LineLineIntersectorTest;
import br.org.archimedes.io.xml.parsers.LineParserTest;
import br.org.archimedes.intersectors.LinePolylineIntersectorTest;
import br.org.archimedes.line.LineTest;
import br.org.archimedes.intersectors.LineTextIntersectorTest;
import br.org.archimedes.trimmers.LineTrimTest;
import br.org.archimedes.gui.actions.LoadCommandTest;
import br.org.archimedes.controller.commands.MacroCommandTest;
import br.org.archimedes.mirror.MirrorFactoryTest;
import br.org.archimedes.gui.model.MousePositionManagerTest;
import br.org.archimedes.move.MoveCommandTest;
import br.org.archimedes.move.MoveElementTest;
import br.org.archimedes.move.MoveFactoryTest;
import br.org.archimedes.offset.OffsetFactoryTest;
import br.org.archimedes.orto.OrtoCommandTest;
import br.org.archimedes.controller.commands.PanCommandTest;
import br.org.archimedes.paste.PasteFactoryTest;
import br.org.archimedes.model.PointTest;
import br.org.archimedes.polyline.PolyLineFactoryTest;
import br.org.archimedes.polyline.PolyLineTest;
import br.org.archimedes.io.svg.elements.PolylineExporterTest;
import br.org.archimedes.extenders.PolylineExtenderTest;
import br.org.archimedes.intersectors.PolylinePolylineIntersectorTest;
import br.org.archimedes.trimmers.PolylineTrimTest;
import br.org.archimedes.controller.commands.PutElementTest;
import br.org.archimedes.polyline.rectangle.RectangleFactoryTest;
import br.org.archimedes.model.RectangleTest;
import br.org.archimedes.redo.RedoTest;
import br.org.archimedes.controller.commands.RemoveElementTest;
import br.org.archimedes.rotate.RotateFactoryTest;
import br.org.archimedes.rotate.RotateTest;
import br.org.archimedes.io.svg.SVGExporterHelperTest;
import br.org.archimedes.io.svg.SVGExporterTest;
import br.org.archimedes.gui.actions.SaveCommandTest;
import br.org.archimedes.scale.ScaleFactoryTest;
import br.org.archimedes.model.SelectionTest;
import br.org.archimedes.intersectors.SemilineArcIntersectorTest;
import br.org.archimedes.intersectors.SemilineCircleIntersectorTest;
import br.org.archimedes.io.svg.elements.SemilineExporterTest;
import br.org.archimedes.extenders.SemilineExtenderTest;
import br.org.archimedes.intersectors.SemilineInfiniteLineIntersectorTest;
import br.org.archimedes.intersectors.SemilineLineIntersectorTest;
import br.org.archimedes.intersectors.SemilinePolylineIntersectorTest;
import br.org.archimedes.intersectors.SemilineSemilineIntersectorTest;
import br.org.archimedes.semiline.SemilineTest;
import br.org.archimedes.trimmers.SemilineTrimTest;
import br.org.archimedes.parser.SimpleSelectionParserTest;
import br.org.archimedes.snap.SnapTest;
import br.org.archimedes.stretch.StretchFactoryTest;
import br.org.archimedes.io.svg.elements.TextExporterTest;
import br.org.archimedes.parser.TextParserTest;
import br.org.archimedes.text.tests.TextTest;
import br.org.archimedes.trims.TrimCommandTest;
import br.org.archimedes.trims.TrimManagerTest;
import br.org.archimedes.controller.commands.TrimTest;
import br.org.archimedes.undo.UndoTest;
import br.org.archimedes.UtilsTest;
import br.org.archimedes.parser.VectorParserTest;
import br.org.archimedes.model.VectorTest;
import br.org.archimedes.infiniteLine.VerticalInfiniteLineTest;
import br.org.archimedes.gui.model.VisualHelperTest;
import br.org.archimedes.gui.model.WorkspaceTest;
import br.org.archimedes.io.xml.XMLExporterHelperTest;
import br.org.archimedes.io.xml.XMLExporterTest;
import br.org.archimedes.io.xml.XMLImporterTest;
import br.org.archimedes.controller.commands.ZoomByAreaCommandTest;
import br.org.archimedes.zoom.ZoomFactoryTest;
import br.org.archimedes.parser.ZoomParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ActiveStateTest.class,
	ArcArcIntersectorTest.class,
	ArcExporterTest.class,
	ArcExtenderTest.class,
	ArcFactoryTest.class,
	ArcInfiniteLineIntersectorTest.class,
	ArcLineIntersectorTest.class,
	ArcTest.class,
	ArcTrimTest.class,
	AreaPerimeterFactoryTest.class,
	CircleExporterTest.class,
	CircleFactoryTest.class,
	CircleLineIntersectorTest.class,
	CirclePolylineIntersectorTest.class,
	CircleTest.class,
	CircleTrimTest.class,
	CommandParserTest.class,
	CopyPasteFactoryTest.class,
	CopyToClipboardFactoryTest.class,
	DefaultFilleterTest.class,
	DimensionExporterTest.class,
	DimensionFactoryTest.class,
	DimensionParserTest.class,
	DimensionTest.class,
	DistanceFactoryTest.class,
	DistanceParserTest.class,
	DoubleDecoratorParserTest.class,
	DrawingIntersectionTest.class,
	DrawingTest.class,
	EditTextCommandTest.class,
	EditTextFactoryTest.class,
	EraseFactoryTest.class,
	ExplodeFactoryTest.class,
	ExtendCommandTest.class,
	ExtendManagerTest.class,
	ExtendTest.class,
	FilletCommandTest.class,
	FilletFactoryTest.class,
	GeometricsTest.class,
	HorizontalInfiniteLineTest.class,
	InfiniteLineExporterTest.class,
	InfiniteLinePolylineIntersectorTest.class,
	InfiniteLineTrimTest.class,
	InputControllerTest.class,
	LeaderExporterTest.class,
	LeaderTest.class,
	LineExporterTest.class,
	LineExtenderTest.class,
	LineFactoryTest.class,
	LineInfiniteLineIntersectorTest.class,
	LineLeaderIntersectorTest.class,
	LineLineIntersectorTest.class,
	LineParserTest.class,
	LinePolylineIntersectorTest.class,
	LineTest.class,
	LineTextIntersectorTest.class,
	LineTrimTest.class,
	LoadCommandTest.class,
	MacroCommandTest.class,
	MirrorFactoryTest.class,
	MousePositionManagerTest.class,
	MoveCommandTest.class,
	MoveElementTest.class,
	MoveFactoryTest.class,
	OffsetFactoryTest.class,
	OrtoCommandTest.class,
	PanCommandTest.class,
	PanCommandTest.class,
	PasteFactoryTest.class,
	PointTest.class,
	PolyLineFactoryTest.class,
	PolyLineTest.class,
	PolylineExporterTest.class,
	PolylineExtenderTest.class,
	PolylinePolylineIntersectorTest.class,
	PolylineTrimTest.class,
	PutElementTest.class,
	RectangleFactoryTest.class,
	RectangleTest.class,
	RedoTest.class,
	RemoveElementTest.class,
	RotateFactoryTest.class,
	RotateTest.class,
	SVGExporterHelperTest.class,
	SVGExporterTest.class,
	SaveCommandTest.class,
	ScaleFactoryTest.class,
	SelectionTest.class,
	SemilineArcIntersectorTest.class,
	SemilineCircleIntersectorTest.class,
	SemilineExporterTest.class,
	SemilineExtenderTest.class,
	SemilineInfiniteLineIntersectorTest.class,
	SemilineLineIntersectorTest.class,
	SemilinePolylineIntersectorTest.class,
	SemilineSemilineIntersectorTest.class,
	SemilineTest.class,
	SemilineTrimTest.class,
	SimpleSelectionParserTest.class,
	SnapTest.class,
	StretchFactoryTest.class,
	TextExporterTest.class,
	TextParserTest.class,
	TextTest.class,
	TrimCommandTest.class,
	TrimManagerTest.class,
	TrimTest.class,
	UndoTest.class,
	UtilsTest.class,
	VectorParserTest.class,
	VectorTest.class,
	VerticalInfiniteLineTest.class,
	VisualHelperTest.class,
	WorkspaceTest.class,
	XMLExporterHelperTest.class,
	XMLExporterTest.class,
	XMLImporterTest.class,
	ZoomByAreaCommandTest.class,
	ZoomFactoryTest.class,
	ZoomParserTest.class
})
public class RunAll{}
