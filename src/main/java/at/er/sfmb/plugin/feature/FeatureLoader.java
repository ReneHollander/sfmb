package at.er.sfmb.plugin.feature;

import com.google.common.reflect.ClassPath;

import java.io.IOException;

public class FeatureLoader {

    public static void loadFromPackage(FeatureManager fm, String packageName) {
        try {
            for (final ClassPath.ClassInfo info : ClassPath.from(FeatureLoader.class.getClassLoader()).getTopLevelClasses()) {
                if (info.getName().startsWith(packageName + ".")) {
                    final Class<?> clazz = info.load();
                    Class<? extends Feature> featureClass = clazz.asSubclass(Feature.class);
                    fm.registerFeature(featureClass);
                }
            }
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

}
