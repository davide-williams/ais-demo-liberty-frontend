echo ' <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <groupId>gov.ais.demo</groupId>
        <artifactId>front-end-service</artifactId>
        <version>'$BUILD_NUMBER'</version>
        <packaging>war</packaging>

        <build>
                <finalName>'$APP_NAME'</finalName>
                <plugins>
                        <plugin>
                                <artifactId>maven-compiler-plugin</artifactId>
                                <version>3.8.1</version>
                                <configuration>
                                        <source>1.8</source>
                                        <target>1.8</target>
                                </configuration>
                        </plugin>
                        <plugin>
                                <artifactId>maven-war-plugin</artifactId>
                                <version>3.3.1</version>
                                <configuration>
                                        <failOnMissingWebXml>false</failOnMissingWebXml>
                                </configuration>
                        </plugin>                      
                </plugins>
        </build>

        <dependencies>

                <!-- The Java EE 7 API, which is the main and only API which this project
                        is providing samples for. -->
                <dependency>
                        <groupId>javax</groupId>
                        <artifactId>javaee-api</artifactId>
                        <version>7.0</version>
                        <scope>provided</scope>
                </dependency>

                <dependency>
                        <groupId>com.ibm.rules</groupId>
                        <artifactId>jrules-engine</artifactId>
                        <scope>system</scope>
                        <version>1.0</version>
                        <systemPath>${project.basedir}/lib/jrules-engine.jar</systemPath>
                </dependency>

                <dependency>
                        <groupId>commons-codec</groupId>
                        <artifactId>commons-codec</artifactId>
                        <version>1.15</version>
                </dependency>
                <dependency>
                        <groupId>commons-logging</groupId>
                        <artifactId>commons-logging</artifactId>
                        <version>1.2</version>
                </dependency>

                <dependency>
                        <groupId>com.fasterxml.jackson.core</groupId>
                        <artifactId>jackson-databind</artifactId>
                        <version>2.13.3</version>
                </dependency>
                <dependency>
                        <groupId>com.fasterxml.jackson.dataformat</groupId>
                        <artifactId>jackson-dataformat-xml</artifactId>
                        <version>2.13.3</version>
                </dependency>
                <dependency>
                        <groupId>com.ibm.mq</groupId>
                        <artifactId>com.ibm.mq.allclient</artifactId>
                        <version>9.3.0.0</version>
                </dependency>
                <dependency>
                        <groupId>javax.validation</groupId>
                        <artifactId>validation-api</artifactId>
                        <version>2.0.1.Final</version>
                </dependency>

                <!-- Test dependencies which can be used in sub-modules -->

                <dependency>
                        <groupId>junit</groupId>
                        <artifactId>junit</artifactId>
                        <version>4.13.1</version>
                        <scope>test</scope>
                </dependency>
                <dependency>
                        <groupId>io.quarkus</groupId>
                        <artifactId>quarkus-junit4</artifactId>
                        <version>0.22.0</version>
                        <scope>test</scope>
                </dependency>                
        </dependencies>
</project> ' > pom.xml