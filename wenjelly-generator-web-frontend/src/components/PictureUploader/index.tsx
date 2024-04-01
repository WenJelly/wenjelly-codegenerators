import { uploadFileUsingPost } from '@/services/backend/fileController';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { message, Upload, UploadProps } from 'antd';
import React, { useState } from 'react';

export const COS_HOST = 'https://code-generator-1325426290.cos.ap-guangzhou.myqcloud.com';

interface Props {
  biz: string;
  onChange?: (url: string) => void;
  value?: string;
}

/**
 * 主页
 * @constructor
 */
const PictureUploader: React.FC<Props> = (props) => {
  const { biz, value, onChange } = props;

  const [loading, setLoading] = useState<boolean>(false);
  const uploadProps: UploadProps = {
    name: 'file',
    // 是否能多选文件
    multiple: false,
    listType: 'picture',
    // 最大上传个数
    maxCount: 1,
    showUploadList: false,
    disabled: loading,
    customRequest: async (fileObj: any) => {
      setLoading(true);
      try {
        const res = await uploadFileUsingPost(
          {
            biz,
          },
          {},
          fileObj.file,
        );
        // 拼接完整路径
        const fullPath = COS_HOST + res.data;
        // 回调函数
        onChange?.(fullPath ?? '');
        fileObj.onSuccess(res.data);
      } catch (error: any) {
        message.error('上传失败,' + error.error);
        fileObj.onError(error);
      }
      setLoading(false);
    },
  };

  const uploadButton = (
    <button style={{ border: 0, background: 'none' }} type="button">
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>上传</div>
    </button>
  );

  return (
    <Upload {...uploadProps}>
      {value ? <img src={value} alt="picture" style={{ width: '100%' }} /> : uploadButton}
    </Upload>
  );
};

export default PictureUploader;
