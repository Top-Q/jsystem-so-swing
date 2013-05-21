/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package org.jsystem.objects.handlers;

public class ExampleHandlersList implements HandlersList {

	@Override
	public HandlerBasic[] getHandlersList() {
		return new HandlerBasic[] {new ExampleHandler()};
	}

}
