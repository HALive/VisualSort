VisualSort
==========
VisualSort is a tool written in Java that visualizes some sorting alogorithms.
The visualisation can either get rendered with Java2D (modified for active rendering) or OpenGL (Slick2D)
OpenGL is only supported by platforms supported by LWJGL 2. After choosing OpenGL as renderer the window CANNOT get resized.


Compiling
-------
Before trying to compile VisualSort, you have to supply a jar version of the Slick2D Library(named as slick.jar) and a jar version of IBXM (named ibxm.jar) in the libs folder.

Once this step is completed you can compile it by running

    gradlew build
