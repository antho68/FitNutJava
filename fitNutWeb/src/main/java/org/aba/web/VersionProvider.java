package org.aba.web;

import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class VersionProvider implements Serializable
{
    private static final long serialVersionUID = 4281342059195584991L;
    private static final VersionProvider INSTANCE = new VersionProvider();
    private String version = "UnknownVersion";

    private VersionProvider()
    {
        this.initFromManifest();
    }

    public static String getVersion()
    {
        return INSTANCE.version;
    }

    public static String getMajorVersionNo()
    {
        return StringUtils.substringBefore(getVersion(), ".");
    }

    private void initFromManifest()
    {
        if (CommonUtils.getServletContext() != null)
        {
            try
            {
                InputStream stream = CommonUtils.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
                Throwable var2 = null;

                try
                {
                    Properties properties = new Properties();
                    if (stream != null)
                    {
                        properties.load(stream);
                    }

                    this.version = properties.getProperty("Implementation-Version", "UnknownVersion");
                }
                catch (Throwable var12)
                {
                    var2 = var12;
                    throw var12;
                }
                finally
                {
                    if (stream != null)
                    {
                        if (var2 != null)
                        {
                            try
                            {
                                stream.close();
                            }
                            catch (Throwable var11)
                            {
                                var2.addSuppressed(var11);
                            }
                        }
                        else
                        {
                            stream.close();
                        }
                    }

                }
            }
            catch (IOException var14)
            {
                CommonUtils.logError(ConstantsWeb.ERROR_LOG, "Error reading /META-INF/MANIFEST.MF properties!");
            }

        }
    }
}

