[![CI](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml/badge.svg)](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
[![Maven Central](https://maven-badges.sml.io/sonatype-central/io.github.m2ci-msp/jtgt/badge.svg)](https://central.sonatype.com/artifact/io.github.m2ci-msp/jtgt)

# JTGT

JTGT is a Java library for loading, manipulating, and writing [Praat] TextGrid files, and similar annotation files, such as XWaves lab.
It's inspired by the [TextGridTools] module for Python.

## Examples

### Loading a TextGrid file

```java
String tgStr = new File("path/to/my.TextGrid").getText();
TextGrid tg = new TextGridSerializer().fromString(tgStr);
```

### Iterating over intervals

```java
for (Annotation annot : tg.getTiers().get(0).getAnnotations()) {
    System.out.println(annot.getText());
}
```

## [Javadoc]

[Praat]: https://praat.org/
[TextGridTools]: https://github.com/hbuschme/TextGridTools
[Javadoc]: https://m2ci-msp.github.io/jtgt/docs/javadoc/
