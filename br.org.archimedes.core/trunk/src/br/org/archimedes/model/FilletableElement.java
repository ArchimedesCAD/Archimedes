///**
// * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
// * All rights reserved. This program and the accompanying materials are made available under the
// * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html<br>
// * <br>
// * Contributors:<br>
// * Hugo Corbucci - initial API and implementation<br>
// * <br>
// * This file was created on 2006/05/29, 00:03:02, by Hugo Corbucci.<br>
// * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
// */
//package br.org.archimedes.model;
//
///**
// * Belongs to package br.org.archimedes.model.
// */
//public interface FilletableElement {
//
//    /**
//     * Extends or retracts the element to the intersection point depending where
//     * is the intersection point. If the intersection point is inside the
//     * element it will do a retraction, otherwise it will be a extension.
//     * 
//     * @param intersection
//     *            The intersection point
//     * @param direction
//     *            Point that determines the direction of the retraction, if
//     *            needed
//     * @return The element created by the fillet
//     */
//    public Element fillet (Point intersection, Point direction);
////
////    /**
////     * Gets the segment from the intersection point to a point of the element.
////     * This segment represents the part of the element that must be extended or
////     * cutted.
////     * 
////     * @param intersection
////     *            The intersection point between the two elements involved in
////     *            the fillet
////     * @param click
////     *            The clicked point that determines the fillet direction
////     * @return The fillet segment or null if it is not possible to extend or cut
////     *         the element using the intersection point
////     */
////    public Line getFilletSegment (Point intersection, Point click);
//}
