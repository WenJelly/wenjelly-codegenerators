import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
import React from 'react';

const Footer: React.FC = () => {
  const defaultMessage = 'WenJelly';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'codeNav',
          title: 'Test',
          href: 'https://wenjelly.cn',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Test',
          href: 'https://wenjelly.cn',
          blankTarget: true,
        },
        {
          key: 'github',
          title: (
            <>
              <GithubOutlined /> Test
            </>
          ),
          href: 'https://wenjelly.cn',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
