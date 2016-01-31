/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package bropals.lib.simplegame.io;

import bropals.lib.simplegame.logger.ErrorLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Super class for all asset loaders.
 *
 * @author Kevin Prehn
 * @param <T> the type of assets that this AssetLoader will load.
 */
public abstract class AssetLoader<T> {
    
    private final HashMap<String, T> assets = new HashMap<>();
    /**
     * The asset manager this asset loader is inside.
     */
    private AssetManager assetManager;
        
    /**
     * Puts an asset into this AssetLoader's assets cache.
     * @param key the key to store the asset at
     * @param asset the asset to add
     */
    protected void add(String key, T asset) {
        assets.put(key, asset);
    }
    
    /**
     * Gets a loaded asset from this AssetLoader.
     * @param key the key the asset was stored as
     * @return the loaded asset
     */
    public T getAsset(String key) {
        return assets.get(key);
    }
    
    void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    /**
     * Gets the AssetManager that this AssetLoader is inside.
     * @return the AssetManager that this AssetLoader is inside.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    /**
     * Loads an asset from an InputStream. Implementations of AssetLoader must,
     * at some point, <code>add</code> the asset loaded form the input stream
     * to this AssetLoader as the given key.
     * @param key the key to store the asset as
     * @param inputStream the input stream to load the asset from
     */
    public abstract void loadAsset(String key, InputStream inputStream);
    
    /**
     * Loads an asset from a file.
     * @param key the key to store the asset as
     * @param file the file to load the asset from.
     */
    public void loadAsset(String key, File file) {
        try {
            loadAsset(key, new FileInputStream(file));
        } catch (IOException ex) {
            ErrorLogger.println("Could not open InputStream with file: " + 
                    file.toString() + ": " + ex);
        }
    }
    
    /**
     * Loads an asset from a URL.
     * @param key the key to store the asset as
     * @param url the URL to load the asset from
     */
    public void loadAssetFromURL(String key, URL url) {
        try {
            loadAsset(key,
                    url.openStream()
            );
        } catch (IOException ex) {
            ErrorLogger.println("Could not open InputStream with URL: " + 
                    url.toString() + ": " + ex);
        }
    }
    
    /**
     * Removes the asset with the specified key from memory.
     * @param key the key of the asset to remove
     */
    public void unload(String key) {
        assets.remove(key);
    }
}
