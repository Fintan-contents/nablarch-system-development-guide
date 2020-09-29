# Customization of UI Standards

## UI standard (screen)

Customize the UI standard (screen) according to your requirements.   
The main parts that need to be customized are as follows. 
- Modification of supported browsers (required)
- Color scheme (or customization of color scheme of CSS framework)
- Font (or customization of CSS framework font)
- Area size
  (UI development base is described in px, but if you use a CSS framework that employs a grid, you need to rewrite to describe the assumption)

## UI standard (screen) separate volume_UI parts catalog

Customize the UI standard (screen) separate volume_UI parts catalog according to your requirements.

- Create a catalog for the specific set of parts that you will use within the CSS framework.   
  The reasons for using the specific parts are as follows.
  - In the CSS framework, there may be several methods (components) to achieve a single function.
  - Depending on the component, because of its richness, there may be difficulty in converting HTML to JSP tags. This is because it is more productive to create a HTML design using only convertible parts.
