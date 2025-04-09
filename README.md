Features:

- MultiModule App
  -
      :app - main module. responsible for app initializing
      :core-navigation-api - provides interfaces for navigation. 
          Should be included to feature modules
      :core-navigation-impl - implementation of navigation.
      :core-ui - themes, colors, etc
      :feature-auth - identification of the user, registration. In plans: google auth and firebase
      :feature-movies - Now showing & coming soon movies. Details. IMDB/TRAKT Apis
      // TODO add other modules to description
- Technologies/Frameworks:
  - 
      UI:
        - Compose,
        - MVI [WIP]
      DI: 
        - Hilt 
- [WORK in Progress]