# Screen Mockup Creation Guide

Screen mock-ups are used for the following purposes.

- Matching the screen image with the customer requirement
- Screenshot of system function design document
- Input of JSP creation in PG phase

## Folder structure

Create a directory with a function name such as project, login, and place the HTML file in the directory.

## Template for HTML

Use the existing HTML as a template.

## Coding rules

### Layout

The layout used for the part is the 16 columns grid system, which is the standard for the Semantic UI.

Reference： https://semantic-ui.com/collections/grid.html

### CSS class to use

Use CSS class defined in Semantic UI or CSS defined in existing HTML.

- [Semantic UI Elements](https://semantic-ui.com/elements/button.html) *
- [Semantic UI Collections](https://semantic-ui.com/collections/form.html) *

Since the Website does not have an appropriate index page, the link is to a specific part page.  
When you view the page, a menu is displayed on the left, please search for parts from that menu.

If you cannot implement with the above CSS, consult with the architect.  
After consultation, if a screen-specific CSS class is required, define the CSS class in the HTML head element.

### Format

Use VSCode formatter.   
Apply the formatter before committing.

Reference： https://qiita.com/rubytomato@github/items/d4716c41a2d15c64f791

## Other rules

- Use only HTML tags. Do not use custom JSP tags.
- Use parts that can be converted from HTML to JSP tags.
  For example, do not use button components that are not a tags or form tags.
- Do not assign unnecessary CSS classes.
  As JSP is created based on HTML, it causes unnecessary style specification for JSP.

