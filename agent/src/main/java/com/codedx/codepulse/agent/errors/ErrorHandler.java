/* Code Pulse: a real-time code coverage tool, for more information, see <http://code-pulse.com/>
 *
 * Copyright (C) 2014-2017 Code Dx, Inc. <https://codedx.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codedx.codepulse.agent.errors;

import java.util.LinkedList;

/**
 * Error manager handles error reporting
 * @author RobertF
 */
public final class ErrorHandler
{
	private static final LinkedList<ErrorListener> listeners = new LinkedList<ErrorListener>();

	public static void addListener(ErrorListener listener)
	{
		listeners.add(listener);
	}

	public static void removeListener(ErrorListener listener)
	{
		listeners.remove(listener);
	}

	public static void handleError(String errorMessage)
	{
		if (!listeners.isEmpty())
			for (ErrorListener listener : listeners)
				listener.onErrorReported(errorMessage, null);
	}

	public static void handleError(String errorMessage, Exception exception)
	{
		if (!listeners.isEmpty())
			for (ErrorListener listener : listeners)
				listener.onErrorReported(errorMessage, exception);
	}
}
