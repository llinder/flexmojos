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
package org.sonatype.flexmojos.tests.issues;

import org.testng.annotations.Test;

public class Issue0076Test
    extends AbstractIssueTest
{

    @Test
    public void notDeclared()
        throws Exception
    {
        // will not generate bundle and will not merge it
        super.testIssue( "issue-0076" );
    }

    @Test
    public void merged()
        throws Exception
    {
        super.testIssue( "issue-0076", "-Pmerge-true" );
    }

    @Test
    public void notMerged()
        throws Exception
    {
        super.testIssue( "issue-0076", "-Pmerge-false" );
    }

}
