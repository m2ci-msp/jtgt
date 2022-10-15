[![CI](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml/badge.svg)](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)

# JTGT

JTGT is a Java library for loading, manipulating, and writing [Praat] TextGrid files, and similar annotation files, such as XWaves lab.
It's inspired by the [TextGridTools] module for Python.

## Dependency Information

Snapshot builds are [OSSRH].
Release versions are hosted on [OSSRH] and indexed in [Maven Central].

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
[OSSRH]: https://s01.oss.sonatype.org/
[Maven Central]: https://search.maven.org/
[Javadoc]: https://m2ci-msp.github.io/jtgt/docs/javadoc/
