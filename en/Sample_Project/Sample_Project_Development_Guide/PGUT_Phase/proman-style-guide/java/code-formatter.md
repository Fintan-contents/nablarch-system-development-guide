# Java code formatter

If your project doesn't have a consistent code style, your code will be less readable.
Using code formatters with the same settings is effective for unifying code style.
By dealing with it mechanically, there is no need to describe it in the coding convention, and only important matters can be described in the coding convention.

IDE such as IntelliJ IDEA and Eclipse provide code formatter functionality.
Code formatter defaults to code styles that are generally considered to be readable.
If customization is required, customized settings can be exported, imported, and shared among project developers.

Also, even if you use a mix of IDEs, by using a tool that defines code style, you can at least unify the code style.

Code style can be unified in the project by the developer setting code formatter and executing code formatter after changing code.

Note that if the developer run the code formatter manually, it is possible that they may forget to run it.
To prevent such a situation, you can set automatic execution setting described later, or check by adding rules related to code style such as indentation and white space to CheckStyle.

## IntelliJ IDEA

### How to set up

Please refer to the following page for how to set code formatter.

- [Code style schemes | IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/configuring-code-style.html)

#### Setting Example

[here](./assets/codestyle/intellij_formatter.xml) is a profile with the following changes from the default settings.
(Based on the `Default` profile of IntelliJ IDEA Community Edition `2023.1.4`)

- Imports 
  - Class count to use import with '*'
    - Changed to `99`
  - Names count to use static import with '*'
    - Changed to `99`

### How to Execute

Please refer to the following page for instructions on how to run code formatter.
It is recommended to set it to run automatically when saving to ensure that it will run.

[Reformat code | IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/reformat-and-rearrange-code.html)

### Import and export settings

Code style settings can be exported and shared among project developers.
For more information on export and import, please refer to the following pages.

[Import and export schemes | IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/configuring-code-style.html#import-export-schemes)

## Eclipse

### How to set up

From Eclipse preferences, open `Java > Code Style > Formatter`.
By selecting the profile to be set and clicking the `Edit` button, you can set the code style for the selected profile.

For other details, please refer to the following pages.

- [Code Formatter Preferences | Eclipse Platform](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.user/reference/preferences/java/codestyle/ref-preferences-formatter.htm?cp=1_4_4_0_2_2)

#### Setting Example

- Indentation
  - Tab policy
    - Changed to `Spaces only`
  - Indented elements
    - Statements within 'switch' body
      - Checked

### How to Execute

If you select a file and execute it, you can execute it by opening the target file in the editor and pressing `Ctrl + Shift + F`.

If you want it to run automatically, open `Java > Editor > Save Actions` from Eclipse Preferences and select `Perform the selected actions on save` and `Format source code`.
This setting will automatically run code formatter on save.
To ensure that code formatter runs, we recommend setting it to run automatically on save.

For other details, please refer to the following pages.

- [Formatter | Eclipse Platform](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fref-java-editor-formatter.htm&cp%3D1_4_1_1)
- [Java Save Actions Preferences | Eclipse Platform](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Feditor%2Fref-preferences-save-actions.htm&cp%3D1_4_4_0_5_4)

### Import and export settings

Code style settings can be exported and shared among project developers.

From Eclipse preferences, open `Java > Code Style > Formatter`.
You can export the selected profile by selecting the profile to be set and clicking the `Export All` button.
To import, click the `Import` button and select the exported file.

For other details, please refer to the following pages.

- [Code Formatter Preferences | Eclipse Platform](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.user/reference/preferences/java/codestyle/ref-preferences-formatter.htm?cp=1_4_4_0_2_2)

## EditorConfig

[EditorConfig](https://editorconfig.org/) is a tool for defining code style.
Although there are few configurable items, it can be used with various editors and IDEs, and can be used to unify the minimum code style.

### How to set up

Create an `.editorconfig` file in your project and define properties.
For details, please refer to the following pages.

- [What is EditorConfig? | EditorConfig](https://editorconfig.org/#overview)

#### Setting Example

Include a `.editorconfig` file with the following code style settings.

- Format target is Java source file
- Character code is UTF-8
- Line break code is LF
- use spaces for indentation
- Indent depth is `4`
- remove trailing whitespace
- the file ends with a newline

```properties
root = true

[*.java]
charset = utf-8
end_of_line = lf
indent_style = space
indent_size = 4
tab_width = 4
trim_trailing_whitespace = true
insert_final_newline = true
```

### How to Execute

[Editors and IDEs that support EditorConfig](https://editorconfig.org/#pre-installed) run the code formatter normally.

Unsupported editors and IDEs will need to install a [plugin](https://editorconfig.org/#download). After installation, run the code formatter as usual.

In IntelliJ IDEA and Eclipse, the code style set in EditorConfig is given priority, but the code style set in the IDE is also applied.
Please note that the code style that can be unified with EditorConfig is only the part that can be set with EditorConfig.
