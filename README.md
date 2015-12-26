VisualSort
==========
[![Build Status](https://travis-ci.org/HALive/VisualSort.svg)](https://travis-ci.org/HALive/VisualSort)

VisualSort is a tool written in Java that visualizes some sorting alogorithms.
The visualisation can either get rendered with Java2D (modified for active rendering) or OpenGL (Slick2D)
OpenGL is only supported by platforms supported by LWJGL 2. After choosing OpenGL as renderer the window CANNOT get resized.

Compiling
-------
Before trying to compile VisualSort, you have to supply a jar version of the Slick2D Library(named as slick.jar) and a jar version of IBXM (named ibxm.jar) in the libs folder.

Once this step is completed you can compile it by running

    gradlew build

Other Stuff
-------
Many of the Implementations of the sorting algorithms are inspired by the Implementations found on the corresponding Wikipedia Page of the algorithm.

The non-default Icons are taken from the OpenIconLibrary:
<a href="">Open Icon Library</a>

The exported Visualisation of the sorting algorithms is inspired by sortvis:
<a href="http://sortvis.org">Sortvis</a>