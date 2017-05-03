package tarce.support;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Observer;

public class AvatarHelper
{
	private final static String TAG = AvatarHelper.class.getSimpleName();
	public final static String DIR_KCHAT_WORK_RELATIVE_ROOT = "/"+".Linkloving";
	/** APP的服务器根地址 */
	public final static String APP_ROOT_URL ="app.linkloving.com/linkloving_server-watch/";
	/** 用户头像下载Servlet地址*/
	public final static String AVATAR_DOWNLOAD_CONTROLLER_URL_ROOT ="http://"+APP_ROOT_URL+"UserAvatarDownloadController";
	
	/**
	 * 查看指定用户的头像大图.
	 *
	 * @param activity
	 * @param uid
	 */
	public static void showAvatarImage(Activity activity, String uid, Observer observerIfNoAvatar)
	{
		File cachedAvatarFile = AvatarHelper.getUserCachedAvatar(
				activity, uid);
		if(cachedAvatarFile != null)
			//DC
			;
		else
		{
			if(observerIfNoAvatar != null)
				observerIfNoAvatar.update(null, null);
			else
				//DC
//				WidgetUtils.showToast(activity
//					, activity.getString(R.string.user_info_avatar_image_not_exists), ToastType.INFO);
			;
		}
	}

	/**
	 * 组织返回用户头像文件名.
	 *
	 * @param uid
	 * @param md5ForCachedAvatar
	 * @return
	 */
	public static String getUserCachedAvatarFileName(String uid, String md5ForCachedAvatar)
	{
		if(uid == null || md5ForCachedAvatar == null)
		{
			Log.w(TAG, "无效的参数：uid == null || md5ForCachedAvatar == null!");
			return null;
		}
		return uid+"_"+md5ForCachedAvatar+".jpg";
	}

	/**
	 * 获得下载指定用户头像的URL（<b>服务端将根据用户本地缓存图片来
	 * 智能判断是否要下载</b>（服务器的文件名称与本地一样当然就不需要下载了））.
	 *
	 * @param context
	 * @param userUid 要下载头像的用户uid
	 * @param userLocalCachedAvatar 缓存在本地的用户头像文件名称
	 * @return
	 */
	public static String getUserAvatarDownloadURL(Context context, String userUid, String userLocalCachedAvatar)
	{
		return getUserAvatarDownloadURL(context, userUid, userLocalCachedAvatar, false);
	}
//	/**
//	 * 获得<b>无条件（不管该用户有无本地头像缓存）</b>下载指定用户头像的URL.
//	 * @param context
//	 * @param userUid 要下载头像的用户uid
//	 * @return
//	 */
	public static String getUserAvatarDownloadURL(Context context, String userUid)
	{
		return getUserAvatarDownloadURL(context, userUid, null, true);
	}
//	/**
//	 * 获得下载指定用户头像的完整http地址.
//	 * <p>
//	 * 形如：“http://192.168.88.138:8080/UserAvatarDownloadController?
//	 * action=ad&user_uid=400007&user_local_cached_avatar=400007_91c3e0d81b2039caa9c9899668b249e8.jpg
//	 * &enforceDawnload=0”。
//	 *
//	 * @param context
//	 * @param userUid 要下载头像的用户uid
//	 * @param userLocalCachedAvatar 用户缓存在本地的头像文件名（本参数只在enforceDawnload==false时有意义）
//	 * @param enforceDawnload true表示无论客户端有无提交缓存图片名称本方法都将无条件返回该用户头像（如果头像确实存在的话），否则
//	 * 将据客户端提交上来的可能的本地缓存文件来判断是否需要下载用户头像（用户头像没有被更新过当然就不需要下载了！）
//	 * @return 完整的http文件下载地址
//	 */
	private static String getUserAvatarDownloadURL(Context context, String userUid, String userLocalCachedAvatar
			, boolean enforceDawnload)
	{
		String fileURL = AVATAR_DOWNLOAD_CONTROLLER_URL_ROOT
				+"?action=ad"
				+"&user_uid="+userUid
				+(userLocalCachedAvatar==null?"":"&user_local_cached_avatar="+userLocalCachedAvatar)
				+"&enforceDawnload="+(enforceDawnload?"1":"0");
//				+"&uid="+MyApplication.getInstance(context).getLocalUserInfoProvider().getUser_uid();
		Log.e(TAG, "请求的URL"+fileURL);
		return fileURL;
	}

	/**
	 * 返回存储用户头像的目录.（结尾带反斜线）.
	 *
	 * @param context
	 * @return 如果SDCard等正常则返回目标路径，否则返回null
	 */
	public static String getUserAvatarSavedDirHasSlash(Context context)
	{
		String dir = getUserAvatarSavedDir(context);

		return dir ==  null? null : (dir + "/");
	}
	/**
	 * 返回存储用户头像的目录.
	 *
	 * @param context
	 * @return 如果SDCard等正常则返回目标路径，否则返回null
	 */
	public static String getUserAvatarSavedDir(Context context)
	{
		String dir = null;
		File sysExternalStorageDirectory = Environment.getExternalStorageDirectory();
		if(sysExternalStorageDirectory != null && sysExternalStorageDirectory.exists())
		{
			dir = sysExternalStorageDirectory.getAbsolutePath()
					+DIR_KCHAT_WORK_RELATIVE_ROOT;
		}

		return dir;
	}



	/**
	 * 尝试删除指定uid用户缓存在本地的头像（图片文件）.
	 *
	 * @param context
	 * @param uidToDelete 要删除的用户
	 * @param fileNameExceptToDelete 本参数可为null，不为空则表示：删除时要保留的文件名，此参数用于：刚下载完最新头像后又
	 * 要清理老头像时那么就应该跳过这个最新下载的，否则岂不是白白下载罗！
	 */
	public static void deleteUserAvatar(Context context, String uidToDelete, String fileNameExceptToDelete)
	{
		String avatarDirStr = AvatarHelper.getUserAvatarSavedDir(context);
		File avatarTempDir = new File(avatarDirStr);
		if(avatarTempDir != null && avatarTempDir.exists())
		{
			// 遍历缓存目录下的所有头像
			File[] allCachedAvatars = avatarTempDir.listFiles();
			if(allCachedAvatars != null && allCachedAvatars.length > 0)
			{
				for(File cachedAvatar : allCachedAvatars)
				{
					// 从文件名中取出缓存的用户uid（文件存在的格式形如：400002_0b272fca28252641231a94f63d8e25fa.jpg）
					String cachedAvatarFileName = cachedAvatar.getName();
					int separatorIndex = cachedAvatarFileName.indexOf("_");
					if(cachedAvatarFileName != null && (separatorIndex != -1))
					{
						String cachedUid = cachedAvatarFileName.substring(0, separatorIndex);
						// 如果该缓存正好是本用户的，就意味着这是老头像，删除它吧
						if(cachedUid != null && cachedUid.equals(uidToDelete))
						{
							// 如果要删除的这个文件刚好在受保护之列（应删除但排除在本次之外的文件），则本次保留之
							if(fileNameExceptToDelete != null && fileNameExceptToDelete.equals(cachedAvatarFileName))
								;
							else
								// 删除之
								cachedAvatar.delete();
						}
					}
				}
			}
		}
		else
		{
			Log.d(TAG, "【ChangeAvatar】用户的头像缓存目录不存，上传自已头像操作时无需尝试删除自已的头像缓存.");
		}
	}

	/**
	 * 返回指定用户缓存在本地的头像Bitmap对象.
	 *
	 * @param context
	 * @param uid
	 * @param reqWidth、reqHeight 要转换成的像素数，比如：原图是640*640的大图，但用到的地方只需要200*200的图，
	 * 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640
	 * @return
	 * @see #getUserCachedAvatar(Context, String)
	 */
//	public static Bitmap getUserCachedAvatarBitmap(Context context, String uid, int reqWidth, int reqHeight)
//	{
//		Bitmap bp = null;
//		File cachedAvatar = getUserCachedAvatar(context, uid);
//		if(cachedAvatar != null && cachedAvatar.exists())
//		{
//			bp = BitmapFactory.decodeFile(cachedAvatar.getAbsolutePath()
//					// 裁剪图片的Bitmap（从而缩减内存占用）：裁剪内存占用不影响任何本地文件
//					, BitmapHelper.computeSampleSize2(cachedAvatar.getAbsolutePath(), reqWidth, reqHeight));
//		}
//		return bp;
//	}
	/**
	 * 返回指定用户缓存在本地地的头像File引用.
	 * <p>
	 * 注意：如果同一用户在该用户本地错误地存在多个头像（应该是在更新
	 * 缓存时没有删除成功的情况下发生的），则只返回修改时间为最新的文件（就这样吧，
	 * 更新缓存时会主动删除老的，也就等于程序有机会纠正这个错误）.
	 *
	 * @param context
	 * @param uid
	 * @return 如果指定uid的用户存在本在头像缓存则返回File对象，否则返回null
	 */
	public static File getUserCachedAvatar(Context context, String uid)
	{
		String avatarDirStr = AvatarHelper.getUserAvatarSavedDir(context);
		File avatarTempDir = new File(avatarDirStr);

		File cachedAvatarForRet = null;
		if(uid != null && avatarTempDir != null && avatarTempDir.exists())
		{
			// 遍历缓存目录下的所有头像
			File[] allCachedAvatars = avatarTempDir.listFiles();
			if(allCachedAvatars != null && allCachedAvatars.length > 0)
			{
				for(File cachedAvatar : allCachedAvatars)
				{
					// 从文件名中取出缓存的用户uid（文件存在的格式形如：400002_0b272fca28252641231a94f63d8e25fa.jpg）
					String cachedAvatarFileName = cachedAvatar.getName();
					int separatorIndex = cachedAvatarFileName.indexOf("_");
					if(cachedAvatarFileName != null && (separatorIndex != -1))
					{
						String cachedUid = cachedAvatarFileName.substring(0, separatorIndex);
						// 如果该缓存正好是本用户的，那就直接返回
						if(cachedUid != null && cachedUid.equals(uid))
						{
							// 如果存在多张该用户的头像则取修改时间为最新的一张！
							if(cachedAvatarForRet == null
								|| cachedAvatar.lastModified() > cachedAvatarForRet.lastModified())
							{
								cachedAvatarForRet = cachedAvatar;
							}
//							else
//							{
//								if()
//								{
//									cachedAvatarForRet = cachedAvatar;
//								}
//							}
						}
					}
				}
			}
		}

		return cachedAvatarForRet;
	}

//	/**
//	 * 用户头像上传异步线程.
//	 *
//	 * @author Jack Jiang, 2013-12-13
//	 * @version 1.0
//	 */
//	public static abstract class AvatarUploadAsync extends DataLoadingAsyncTask<Object, Integer, Boolean>
//	{
//		/**
//		 * 此Bitmap对象将用于上传成功后放到UI上显示（没有成功的话，需要手动recycle，以便即时回收内存！） .
//		 * 为何要传入此对象而不干脆在UI要用到时从file中decode呢？因为在上传进行之前已经对原始文件进行了
//		 * 压缩，而压缩时就要decode成bitmap对象，那么干脆就将压缩用完后的对象带过来从而提高效率、节省内存哦。 */
//		private Bitmap bmOfTempAvatar = null;
//
//		public AvatarUploadAsync(Activity parentActivity)
//		{
//			super(parentActivity, parentActivity.getString(R.string.user_info_avatar_uploading));
//		}

//		@Override
//		protected Boolean doInBackground(Object... params)
//		{
//			Boolean result = Boolean.FALSE;
//			if(params != null && params.length == 6)//5)
//			{
//				String filePath = (String)params[0];
//				String fileName = (String)params[1];
//				String serverURL = (String)params[2];
//				String localUserUid = (String)params[3];
//				bmOfTempAvatar = (Bitmap)params[4];
//				// @since 2.1
//				byte[] fileData = (byte[])params[5];
//
////				// 将本地用户的uid作为参数传递到服务端
////				HashMap<String, String> requestProperties = new HashMap<String, String>();
////				// 此参数名注意要与服务端保持一致哦
////				requestProperties.put("user_uid", localUserUid);
////				requestProperties.put("file_name", fileName);   // 因为服务端是支持多文件上传的API，所以此处单独把文件名带过去，方便使用！
//				// 提交文件上传处理
//				result = Boolean.valueOf(
//						AvatarDataUploadHttpHelper.uploadAvatarFile(fileName, localUserUid, fileData)
////						HttpUploadHelper2.uploadFile(filePath, fileName, serverURL, requestProperties)
////						HttpUploadHelper.post(filePath, serverURL, requestProperties)
//						);
//			}
//			else
//				Log.e(AvatarUploadAsync.class.getSimpleName(), "无效的参数个数："+(params == null?0:params.length));
//
//			return result;
//		}
//
//		@Override
//		protected void onPostExecuteImpl(Object _result)
//		{
//			if(_result != null)
//			{
//				Boolean result = (Boolean)_result;
//				if(result)
//					afterSucess(bmOfTempAvatar);
//				else
//				{
//					// 如果上传失败则无需显示bitmap到ui上，此外要手动释放哦，以便节省内存！
//					if(bmOfTempAvatar != null && !bmOfTempAvatar.isRecycled())
//						bmOfTempAvatar.recycle();
//					afterFaild();
//				}
//			}
//	    }
//
//		/**
//		 * 上传头像成功后要调用的方法.
//		 */
//		protected abstract void afterSucess(Bitmap bmOfTempAvatar);
//
//		/**
//		 * 上传头像失败后要调用的方法.
//		 */
//		protected abstract void afterFaild();
//	}
}
