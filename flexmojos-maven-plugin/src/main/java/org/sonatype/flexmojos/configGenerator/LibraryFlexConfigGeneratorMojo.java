/**
 *  Copyright 2008 Marvin Herman Froeder
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */
package org.sonatype.flexmojos.configGenerator;

import static org.sonatype.flexmojos.common.FlexExtension.RB_SWC;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.flexmojos.common.FlexClassifier;
import org.sonatype.flexmojos.compiler.FlexConfigBuilder;
import org.sonatype.flexmojos.compiler.LibraryMojo;
import org.sonatype.flexmojos.test.util.PathUtil;
import org.sonatype.flexmojos.utilities.MavenUtils;

import flex2.tools.oem.Library;
import flex2.tools.oem.Report;
import flex2.tools.oem.internal.OEMConfiguration;

/**
 * Goal which generate the flex-config without compile.
 * 
 * @goal generate-config-swc
 * @since 3.4
 */
public class LibraryFlexConfigGeneratorMojo
    extends LibraryMojo
{
    @Override
    protected void build( Library builder, boolean printConfigurations )
        throws MojoExecutionException
    {
        // we need open issue "feature request", but currently we use workaround
        if ( configuration instanceof OEMConfiguration )
        {
            FlexConfigBuilder configBuilder = createFlexConfigBuilderWithoutBuild( configuration );
            fixConfigReport( configBuilder );

            File fileReport = getReportFile( REPORT_CONFIG );
            try
            {
                configBuilder.write( fileReport );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "An error has ocurried while recording config-report" );
            }
        }
        else
        {
            throw new MojoExecutionException( "Flex-compiler API change, unable to use suggested \"solution\"!" );
        }
    }

    @Override
    protected void writeLinkReport( Report report )
        throws MojoExecutionException
    {
        projectHelper.attachArtifact( project, "xml", FlexClassifier.LINK_REPORT, getReportFile( REPORT_LINK ) );
    }

    @Override
    protected void writeReport( Report report, String type )
        throws MojoExecutionException
    {

    }

    @Override
    protected void configure()
        throws MojoExecutionException, MojoFailureException
    {
        configurationReport = true;

        super.configure();
    }

    @Override
    protected void writeResourceBundle( Report report )
        throws MojoExecutionException
    {
        for ( String locale : runtimeLocales )
        {
            File localePath = MavenUtils.getLocaleResourcePath( resourceBundlePath, locale );
            writeResourceBundle( null, locale, localePath );
        }
    }

    @Override
    protected void writeResourceBundle( String[] bundlesNames, String locale, File localePath )
        throws MojoExecutionException
    {
        FlexConfigBuilder configBuilder =
            createFlexConfigBuilderWithoutBuild( getResourceBundleConfiguration( locale, localePath ) );
        File output = getRuntimeLocaleOutputFile( locale, RB_SWC );
        configBuilder.addOutput( output );
        try
        {
            configBuilder.write( new File( output.getPath().replace( "." + RB_SWC, "-config-report.xml" ) ) );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "An error has ocurried while recording config-report for resource module" );
        }

        projectHelper.attachArtifact( project, RB_SWC, locale, output );
    }

    @Override
    protected void fixConfigReport( FlexConfigBuilder configBuilder )
    {
        super.fixConfigReport( configBuilder );

        if ( runtimeLocales != null )
        {
            configBuilder.addResourceBundleReport( PathUtil.getRelativePath( new File( build.getDirectory() ),
                                                                             getReportFile( REPORT_RESOURCE_BUNDLE ) ) );
        }
    }
}