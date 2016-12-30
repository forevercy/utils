package com.cytmxk.utils.storage;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenyang on 2016/11/14.
 * 实现将assets下的文件按目录结构拷贝到sdcard中
 */

public class AssetCopyer {

    private final String TAG = AssetCopyer.class.getCanonicalName();

    private final String copyDirectory = "image";

    private final Context mContext;
    private final AssetManager mAssetManager;
    private File mAppDirectory;

    public AssetCopyer( Context context ) {
        mContext = context;
        mAssetManager = context.getAssets();
    }

    /**
     *  将assets目录下指定的文件拷贝到sdcard中
     *  @return 是否拷贝成功,true 成功；false 失败
     *  @throws IOException
     */
    public boolean copy() throws IOException {

        List<String> srcFiles = new ArrayList<String>();

        //获取系统在SDCard中为app分配的目录，eg:/sdcard/Android/data/$(app's package)
        //该目录存放app相关的各种文件(如cache，配置文件等)，uninstall app后该目录也会随之删除
        mAppDirectory = mContext.getExternalFilesDir(null);
        if (null == mAppDirectory) {
            return false;
        }

        String[] assets = getAssetsList();
        if (null == assets || assets.length <= 0) {
            return true;
        }

        for( String asset : assets ) {
            //如果不存在，则添加到copy列表
            if( ! new File(mAppDirectory, asset).exists() ) {
                srcFiles.add(asset);
            }
        }

        //依次拷贝到App的安装目录下
        for( String file : srcFiles ) {
            copy(file);
        }

        return true;
    }

    /**
     *  获取需要拷贝的文件列表
     *  @return 文件列表
     *  @throws IOException
     */
    private String[] getAssetsList() throws IOException {
        String[] files = null;
        try{
            files = mAssetManager.list(copyDirectory);
            Log.e(TAG, "files = " + Arrays.toString(files));
            return files;
        }catch(IOException e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     *  执行拷贝任务
     *  @param asset 需要拷贝的assets文件路径
     *  @return 拷贝成功后的目标文件句柄
     *  @throws IOException
     */
    protected File copy( String asset ) throws IOException {

        InputStream source = mAssetManager.open(copyDirectory + File.separator + asset);
        File destinationFile = new File(mAppDirectory, asset);
        destinationFile.getParentFile().mkdirs();
        OutputStream destination = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[1024];
        int nread;

        while ((nread = source.read(buffer)) != -1) {
            if (nread == 0) {
                nread = source.read();
                if (nread < 0)
                    break;
                destination.write(nread);
                continue;
            }
            destination.write(buffer, 0, nread);
        }
        destination.close();

        return destinationFile;
    }

}
