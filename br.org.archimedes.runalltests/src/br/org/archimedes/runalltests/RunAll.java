package br.org.archimedes.runalltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.org.archimedes.*;
import br.org.archimedes.arc.*;
import br.org.archimedes.circle.*;
import br.org.archimedes.controller.*;
import br.org.archimedes.copypaste.*;
import br.org.archimedes.copytoclipboard.*;
import br.org.archimedes.dimension.test.*;
import br.org.archimedes.distance.*;
import br.org.archimedes.erase.*;
import br.org.archimedes.extend.*;
import br.org.archimedes.extenders.*;
import br.org.archimedes.fillet.*;
import br.org.archimedes.infiniteLine.*;
import br.org.archimedes.intersectors.*;
import br.org.archimedes.io.svg.*;
import br.org.archimedes.io.svg.elements.*;
import br.org.archimedes.io.xml.*;
import br.org.archimedes.io.xml.parsers.*;
import br.org.archimedes.io.xml.elements.*;
import br.org.archimedes.leader.*;
import br.org.archimedes.line.*;
import br.org.archimedes.mirror.*;
import br.org.archimedes.move.*;
import br.org.archimedes.offset.*;
import br.org.archimedes.orto.*;
import br.org.archimedes.pan.tests.*;
import br.org.archimedes.paste.*;
import br.org.archimedes.polyline.area.*;
import br.org.archimedes.polyline.explode.*;
import br.org.archimedes.polyline.rectangle.*;
import br.org.archimedes.polyline.*;
import br.org.archimedes.redo.*;
import br.org.archimedes.rotate.*;
import br.org.archimedes.scale.*;
import br.org.archimedes.semiline.*;
import br.org.archimedes.snap.*;
import br.org.archimedes.stretch.*;
import br.org.archimedes.text.edittext.tests.*;
import br.org.archimedes.text.tests.*;
import br.org.archimedes.trims.*;
import br.org.archimedes.trimmers.*;
import br.org.archimedes.controller.commands.*;
import br.org.archimedes.gui.actions.*;
import br.org.archimedes.gui.model.*;
import br.org.archimedes.gui.opengl.*;
import br.org.archimedes.model.*;
import br.org.archimedes.parser.*;
import br.org.archimedes.undo.*;
import br.org.archimedes.zoom.*;

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
	LeaderFactoryTest.class,
	LeaderTest.class,
	LeaderXMLExporterTest.class,
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
	NPointParserTest.class,
	OffsetDirectionParserTest.class,
	OffsetFactoryTest.class,
	OpenGLWrapperTest.class,
	OrtoCommandTest.class,
	OrtoFactoryTest.class,
	br.org.archimedes.controller.commands.PanCommandTest.class,
	br.org.archimedes.pan.tests.PanCommandTest.class,
	PanFactoryTest.class,
	PasteFactoryTest.class,
	PointParserTest.class,
	PointTest.class,
	PolylineExporterTest.class,
	PolylineExtenderTest.class,
	PolyLineFactoryTest.class,
	PolylinePolylineIntersectorTest.class,
	PolyLineTest.class,
	PolylineTrimTest.class,
	PutElementTest.class,
	RectangleFactoryTest.class,
	RectangleTest.class,
	RedoTest.class,
	RemoveElementTest.class,
	RotateFactoryTest.class,
	RotateTest.class,
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
	SVGExporterHelperTest.class,
	SVGExporterTest.class,
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