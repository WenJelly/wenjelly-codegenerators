import {
  testDownloadFileUsingGet,
  testUploadFileUsingPost,
} from '@/services/backend/fileController';
import { InboxOutlined } from '@ant-design/icons';
import { Button, Card, Divider, Flex, message, Upload, UploadProps } from 'antd';
import { saveAs } from 'file-saver';
import React, { useState } from 'react';

export const COS_HOST = 'https://code-generator-1325426290.cos.ap-guangzhou.myqcloud.com';

const { Dragger } = Upload;

/**
 * 主页
 * @constructor
 */
const TestFilePage: React.FC = () => {
  const [value, setvalue] = useState<string>();

  const props: UploadProps = {
    name: 'file',
    // 是否能多选文件
    multiple: false,
    // 最大上传个数
    maxCount: 1,
    customRequest: async (fileObj: any) => {
      try {
        const res = await testUploadFileUsingPost({}, fileObj.file);
        fileObj.onSuccess(res.data);
        // 文件上传成功，将属性赋值过去
        setvalue(res.data);
      } catch (error: any) {
        message.error('上传失败,' + error.error);
        fileObj.onError(error);
      }
    },
    onRemove() {
      setvalue(undefined);
    },
  };
  return (
    <Flex>
      <Card title="文件上传">
        <Dragger {...props}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">Click or drag file to this area to upload</p>
          <p className="ant-upload-hint">
            Support for a single or bulk upload. Strictly prohibited from uploading company data or
            other banned files.
          </p>
        </Dragger>
      </Card>
      <Card title="文件下载">
        <div>文件地址： {COS_HOST + value} </div>
        {/*分隔符*/}
        <Divider />
        <img src={COS_HOST + value} height={200} />
        <Divider />
        <Button
          onClick={async () => {
            const blob = await testDownloadFileUsingGet(
              { filePath: value },
              {
                responseType: 'blob',
              },
            );
            // 使用file saver 下载文件
            // 得到文件原始路径
            const fullPath = COS_HOST + value;
            // 得到文件名
            saveAs(blob, fullPath.substring(fullPath.lastIndexOf('/') + 1));
          }}
        >
          点击下载
        </Button>
      </Card>
    </Flex>
  );
};

export default TestFilePage;
