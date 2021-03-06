/**
 * Flexmojos is a set of maven goals to allow maven users to compile, optimize and test Flex SWF, Flex SWC, Air SWF and Air SWC.
 * Copyright (C) 2008-2012  Marvin Froeder <marvin@flexmojos.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.sonatype.flexmojos.compiler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.plexus.logging.AbstractLogger;
import org.codehaus.plexus.logging.Logger;

public class MapLogger
    extends AbstractLogger
{

    private final Map<String, Throwable> logs;

    public MapLogger()
    {
        super( Logger.LEVEL_DEBUG, "TestLog" );
        this.logs = new LinkedHashMap<String, Throwable>();
    }

    public void debug( String message, Throwable throwable )
    {
        logs.put( message, throwable );
    }

    public void error( String message, Throwable throwable )
    {
        logs.put( message, throwable );
    }

    public void fatalError( String message, Throwable throwable )
    {
        logs.put( message, throwable );
    }

    public Logger getChildLogger( String name )
    {
        return this;
    }

    public Map<String, Throwable> getLogs()
    {
        return logs;
    }

    public void info( String message, Throwable throwable )
    {
        logs.put( message, throwable );
    }

    public void warn( String message, Throwable throwable )
    {
        logs.put( message, throwable );
    }

}
