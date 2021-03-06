import React, { useEffect, useState, Fragment } from 'react';
import { Table, Modal } from 'choerodon-ui/pro';
import { observer } from 'mobx-react-lite';
import { FormattedMessage } from 'react-intl';
import { withRouter, Link } from 'react-router-dom';
import { Page, Content, Header, Permission, Action, Breadcrumb, Choerodon } from '@choerodon/boot';
import { Button, Spin, Tooltip } from 'choerodon-ui';
import pick from 'lodash/pick';
import TimePopover from '../../../components/timePopover';
import { useAppTopStore } from '../stores';
import { useAppServiceStore } from './stores';
import CreateForm from '../modals/creat-form';
import EditForm from '../modals/edit-form';
import ImportForm from './modal/import-form';
import StatusTag from '../components/status-tag';
import { handlePromptError } from '../../../utils';

import './index.less';

const { Column } = Table;
const modalKey1 = Modal.key();
const modalKey2 = Modal.key();
const modalKey3 = Modal.key();
const createModalKey = Modal.key();
const editModalKey = Modal.key();
const modalStyle1 = {
  width: 380,
};
const modalStyle2 = {
  width: 'calc(100vw - 3.52rem)',
};

const ListView = withRouter(observer((props) => {
  const {
    intlPrefix,
    prefixCls,
    listPermissions,
    appServiceStore,
  } = useAppTopStore();
  const {
    intl: { formatMessage },
    AppState: {
      currentMenuType: { projectId },
    },
    listDs,
    appListStore,
  } = useAppServiceStore();
  const [isInit, setIsInit] = useState(true);

  useEffect(() => {
    // 确定dataset加载完毕后才打开创建框
    // 否则会造成dataset实例丢失
    if (isInit && listDs.status === 'ready') {
      const { location: { state } } = props;
      if (state && state.openCreate) {
        openCreate();
      }
      setIsInit(false);
    }
  }, [listDs.status]);

  function refresh() {
    listDs.query();
    appListStore.checkCreate(projectId);
  }

  function renderName({ value, record }) {
    const {
      location: {
        search,
        pathname,
      },
    } = props;
    const canLink = !record.get('fail') && record.get('synchro');
    return (
      <Tooltip title={value} placement="top">
        {canLink ? (
          <Link
            to={{
              pathname: `${pathname}/detail/${record.get('id')}`,
              search,
            }}
          >
            <span className={`${prefixCls}-table-name`}>{value}</span>
          </Link>) : <span>{value}</span>}
      </Tooltip>
    );
  }

  function renderType({ value }) {
    return value && <FormattedMessage id={`${intlPrefix}.type.${value}`} />;
  }

  function renderDate({ value }) {
    return <TimePopover content={value} />;
  }

  function renderUrl({ value }) {
    return (
      <a href={value} rel="nofollow me noopener noreferrer" target="_blank">
        {value ? `../${value.split('/')[value.split('/').length - 1]}` : ''}
      </a>
    );
  }

  function renderStatus({ value, record }) {
    return (
      <StatusTag
        active={value}
        fail={record.get('fail')}
        synchro={record.get('synchro')}
      />
    );
  }

  function renderActions({ record }) {
    const actionData = {
      edit: {
        service: ['devops-service.app-service.update'],
        text: formatMessage({ id: 'edit' }),
        action: openEdit,
      },
      stop: {
        service: ['devops-service.app-service.updateActive'],
        text: formatMessage({ id: 'stop' }),
        action: openStop.bind(this, record),
      },
      run: {
        service: ['devops-service.app-service.updateActive'],
        text: formatMessage({ id: 'active' }),
        action: () => changeActive(true),
      },
      delete: {
        service: ['devops-service.app-service.delete'],
        text: formatMessage({ id: 'delete' }),
        action: handleDelete,
      },

    };
    let actionItems;
    if (record.get('fail')) {
      actionItems = pick(actionData, ['delete']);
    } else if (record.get('synchro') && record.get('active')) {
      actionItems = pick(actionData, ['edit', 'stop']);
    } else if (record.get('active')) {
      return;
    } else {
      actionItems = pick(actionData, ['run', 'delete']);
    }
    return <Action data={Object.values(actionItems)} />;
  }

  function handleCancel(dataSet) {
    const { current } = dataSet;
    if (current.status === 'add') {
      dataSet.remove(current);
    } else {
      current.reset();
    }
  }

  function openCreate() {
    Modal.open({
      key: createModalKey,
      drawer: true,
      style: modalStyle1,
      title: formatMessage({ id: `${intlPrefix}.create` }),
      children: <CreateForm
        refresh={refresh}
        intlPrefix={intlPrefix}
        prefixCls={prefixCls}
      />,
      okText: formatMessage({ id: 'create' }),
    });
  }

  function openImport() {
    Modal.open({
      key: modalKey2,
      drawer: true,
      style: modalStyle2,
      title: formatMessage({ id: `${intlPrefix}.import` }),
      children: <ImportForm
        appServiceStore={appServiceStore}
        intlPrefix={intlPrefix}
        prefixCls={prefixCls}
        refresh={refresh}
      />,
      okText: formatMessage({ id: 'import' }),
    });
  }

  function openEdit() {
    const appServiceId = listDs.current.get('id');

    Modal.open({
      key: editModalKey,
      drawer: true,
      style: modalStyle1,
      title: formatMessage({ id: `${intlPrefix}.edit` }),
      children: <EditForm
        refresh={refresh}
        intlPrefix={intlPrefix}
        prefixCls={prefixCls}
        appServiceId={appServiceId}
      />,
      okText: formatMessage({ id: 'save' }),
    });
  }

  // 因为在应用服务那边做了最近访问前端缓存，如果要删除，也必须要删除这个缓存
  function checkLocalstorage(appId) {
    const recentApp = JSON.parse(localStorage.getItem('recent-app'));
    if (recentApp && recentApp[projectId]) {
      const currentProjectApp = recentApp[projectId];
      const hasData = currentProjectApp.filter(item => item.id !== appId);
      if (hasData.length < currentProjectApp.length) {
        recentApp[projectId] = hasData;
        localStorage.setItem('recent-app', JSON.stringify(recentApp));
      }
    }
  }

  function handleDelete() {
    const record = listDs.current;
    const appId = record.get('id');
    const modalProps = {
      title: formatMessage({ id: `${intlPrefix}.delete.title` }, { name: record.get('name') }),
      children: formatMessage({ id: `${intlPrefix}.delete.des` }),
      okText: formatMessage({ id: 'delete' }),
      okProps: { color: 'red' },
      cancelProps: { color: 'dark' },
    };
    listDs.delete(record, modalProps);
    checkLocalstorage(appId);
  }

  async function changeActive(active) {
    if (!active) {
      Modal.open({
        key: modalKey3,
        title: formatMessage({ id: `${intlPrefix}.stop` }, { name: listDs.current.get('name') }),
        children: <FormattedMessage id={`${intlPrefix}.stop.tips`} />,
        onOk: () => handleChangeActive(active),
        okText: formatMessage({ id: 'stop' }),
      });
    } else {
      handleChangeActive(active);
    }
  }

  async function handleChangeActive(active) {
    try {
      if (await appServiceStore.changeActive(projectId, listDs.current.get('id'), active)) {
        checkLocalstorage(listDs.current.get('id'));
        refresh();
      } else {
        return false;
      }
    } catch (e) {
      Choerodon.handleResponseError(e);
      return false;
    }
  }

  function openStop(record) {
    const id = record.get('id');

    const stopModal = Modal.open({
      key: modalKey3,
      title: formatMessage({ id: `${intlPrefix}.check` }),
      children: <Spin />,
      footer: null,
    });

    appServiceStore.checkAppService(projectId, id).then((res) => {
      if (handlePromptError(res)) {
        const { checkResources, checkRule } = res;
        const status = checkResources || checkRule;
        let childrenContent;

        if (!status) {
          childrenContent = <FormattedMessage id={`${intlPrefix}.stop.tips`} />;
        } else if (checkResources && !checkRule) {
          childrenContent = formatMessage({ id: `${intlPrefix}.has.resource` });
        } else if (!checkResources && checkRule) {
          childrenContent = formatMessage({ id: `${intlPrefix}.has.rules` });
        } else {
          childrenContent = formatMessage({ id: `${intlPrefix}.has.both` });
        }

        const statusObj = {
          title: status ? formatMessage({ id: `${intlPrefix}.cannot.stop` }, { name: listDs.current.get('name') }) : formatMessage({ id: `${intlPrefix}.stop` }, { name: listDs.current.get('name') }),
          // eslint-disable-next-line no-nested-ternary
          children: childrenContent,
          okCancel: !status,
          onOk: () => (status ? stopModal.close() : handleChangeActive(false)),
          okText: status ? formatMessage({ id: 'iknow' }) : formatMessage({ id: 'stop' }),
          footer: ((okBtn, cancelBtn) => (
            <Fragment>
              {okBtn}
              {!status && cancelBtn}
            </Fragment>
          )),
        };
        stopModal.update(statusObj);
      } else {
        stopModal.close();
      }
    }).catch((err) => {
      stopModal.close();
      Choerodon.handleResponseError(err);
    });
  }


  function getHeader() {
    const disabled = !appListStore.getCanCreate;
    const disabledMessage = disabled ? formatMessage({ id: `${intlPrefix}.create.disabled` }) : '';
    return <Header title={<FormattedMessage id="app.head" />}>
      <Permission
        service={['devops-service.app-service.create']}
      >
        <Tooltip title={disabledMessage} placement="bottom">
          <Button
            icon="playlist_add"
            disabled={disabled}
            onClick={openCreate}
          >
            <FormattedMessage id={`${intlPrefix}.create`} />
          </Button>
        </Tooltip>
      </Permission>
      <Permission
        service={['devops-service.app-service.importApp']}
      >
        <Tooltip title={disabledMessage} placement="bottom">
          <Button
            icon="archive"
            onClick={openImport}
          >
            <FormattedMessage id={`${intlPrefix}.import`} />
          </Button>
        </Tooltip>
      </Permission>
      <Button
        icon="refresh"
        onClick={refresh}
      >
        <FormattedMessage id="refresh" />
      </Button>
    </Header>;
  }

  return (
    <Page service={listPermissions}>
      {getHeader()}
      <Breadcrumb />
      <Content className={`${prefixCls}-content`}>
        <Table
          dataSet={listDs}
          border={false}
          queryBar="bar"
          pristine
          className={`${prefixCls}.table`}
          rowClassName="c7ncd-table-row-font-color"
        >
          <Column name="name" renderer={renderName} sortable />
          <Column renderer={renderActions} width="0.7rem" />
          <Column name="code" sortable />
          <Column name="type" renderer={renderType} />
          <Column name="repoUrl" renderer={renderUrl} />
          <Column name="creationDate" renderer={renderDate} sortable />
          <Column name="active" renderer={renderStatus} width="0.7rem" align="left" />
        </Table>
      </Content>
    </Page>
  );
}));

export default ListView;
