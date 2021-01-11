export interface HTModule {
    name: string;
    version: string;
    authors: string[];
    dependencies: string[];
    softDependencies: string[];
    main: string;
    mainPageName: string;
}