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

package com.codedx.codepulse.agent.control;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import com.codedx.codepulse.agent.errors.ErrorHandler;
import com.codedx.codepulse.agent.common.config.RuntimeAgentConfigurationV1;

/**
 * Reads incoming configuration packets (version 1)
 *
 * @author robertf
 */
public class ConfigurationReaderV1 implements ConfigurationReader
{
	@Override
	public RuntimeAgentConfigurationV1 readConfiguration(DataInputStream stream) throws IOException
	{
		// read configuration length
		int configLen = stream.readInt();

		// read configuration data
		byte[] configBuffer = new byte[configLen];
		stream.read(configBuffer, 0, configLen);

		ConfigurationInputStream in = new ConfigurationInputStream(new ByteArrayInputStream(configBuffer));
		try
		{
			return (RuntimeAgentConfigurationV1) in.readObject();
		}
		catch (ClassNotFoundException ex)
		{
			ErrorHandler.handleError("error processing configuration", ex);
			return null;
		}
	}

	protected class ConfigurationInputStream extends ObjectInputStream
	{
		public ConfigurationInputStream(InputStream in) throws IOException {
			super(in);
			enableResolveObject(true);
		}

		@Override
		protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException
		{
			ObjectStreamClass clazz = super.readClassDescriptor();

			// com.secdec.bytefrog.common -> com.codedx.codepulse.agent.common
			if (clazz.getName().startsWith("com.secdec.bytefrog.common."))
				return ObjectStreamClass.lookup(Class.forName(clazz.getName().replace("com.secdec.bytefrog.common.", "com.codedx.codepulse.agent.common.")));

			return clazz;
		}
	}
}
