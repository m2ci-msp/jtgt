[![CI](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml/badge.svg)](https://github.com/m2ci-msp/jtgt/actions/workflows/main.yml)
[![Download](https://api.bintray.com/packages/m2ci-msp/maven/jtgt/images/download.svg)](https://bintray.com/m2ci-msp/maven/jtgt/_latestVersion)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)

# JTGT

JTGT is a Java library for loading, manipulating, and writing [Praat] TextGrid files, and similar annotation files, such as XWaves lab.
It's inspired by the [TextGridTools] module for Python.

## Dependency Information

Release versions are [hosted on Bintray] and indexed in [JCenter].
Snapshot builds are [hosted on OJO].

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

[Praat]: http://praat.org/
[TextGridTools]: https://github.com/hbuschme/TextGridTools
[hosted on Bintray]: https://bintray.com/m2ci-msp/maven/jtgt/_latestVersion
[JCenter]: https://bintray.com/bintray/jcenter
[hosted on OJO]: https://oss.jfrog.org/artifactory/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/org/m2ci/msp/jtgt
[Javadoc]: https://m2ci-msp.github.io/jtgt/docs/javadoc/
