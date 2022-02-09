package com.chillycheesy.modulo.modules;


public class ModuleBuilderTest {

    /*private File file;
    private File configFile;

    @BeforeEach
    public final void init() throws URISyntaxException {
        file = new File(Objects.requireNonNull(ModuleBuilder.class.getClassLoader().getResource("modules/hello-JARJARBINKS-1.0.0.jar")).toURI());
        configFile = new File(Objects.requireNonNull(ModuleBuilder.class.getClassLoader().getResource("module.yml")).toURI());
    }

    @Test
    public final void buildTest() {
        Module module = ModuleBuilder.build(file);
        assertTrue(file.exists());
        assertNotNull(module);
        assertEquals("Hello", module.getName());
        assertEquals("1.0.0", module.getVersion());
        assertEquals(List.of("Owl-e"), module.getAuthors());
        assertEquals(new ArrayList<>(), module.getDependencies());
        assertEquals(new ArrayList<>(), module.getSoftDependencies());
        assertEquals("fr.owle.hello.HelloModule", module.getMain());
    }

    @Test
    public final void readYmlTest() throws IOException, InvalidModuleConfigurationException {
        ModuleConfig config = ModuleBuilder.readYml(configFile);
        assertTrue(configFile.exists());
        assertNotNull(config);
        assertEquals("Hello", config.getName());
        assertEquals("1.0.0", config.getVersion());
        assertEquals(List.of("Owl-e"), config.getAuthors());
        assertEquals(new ArrayList<>(), config.getDependencies());
        assertEquals(new ArrayList<>(), config.getSoftDependencies());
        assertEquals("fr.owle.hello.HelloModule", config.getMain());
    }

    @Test
    public final void buildFailTest() {
        assertNull(ModuleBuilder.build(new File("./")));
    }*/
}
