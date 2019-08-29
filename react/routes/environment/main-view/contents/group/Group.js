import React, { Fragment } from 'react';
import { observer } from 'mobx-react-lite';
import { Action } from '@choerodon/master';
import { Table } from 'choerodon-ui/pro';
import StatusTag from '../../../../../components/status-tag';
import { getEnvStatus } from '../../../../../components/status-dot';
import { useEnvironmentStore } from '../../../stores';
import { useEnvGroupStore } from './stores';
import Modals from './modals';

const { Column } = Table;

const Group = observer(() => {
  const {
    prefixCls,
    intlPrefix,
    envStore: { getSelectedMenu: { id, name } },
    AppState: { currentMenuType: { id: projectId } },
  } = useEnvironmentStore();
  const {
    groupDs,
    intl: { formatMessage },
  } = useEnvGroupStore();

  function refresh() {
  }

  function handleDelete() {
  }

  function renderName({ value, record }) {
    const active = record.get('active');
    const connect = record.get('connect');
    const synchronize = record.get('synchro');
    const status = getEnvStatus(connect, synchronize, active);
    return (
      <Fragment>
        <StatusTag
          colorCode={status}
          name={formatMessage({ id: status })}
        />
        {value}
      </Fragment>
    );
  }

  function renderActions({ record }) {
    const groupId = record.get('id');
    const active = record.get('active');
    const synchronize = record.get('synchro');
    const actionData = active ? [{
      service: [],
      text: formatMessage({ id: 'edit' }),
      // action: handleClick,
    }, {
      service: [],
      text: formatMessage({ id: 'stop' }),
      // action: confirmDelete,
    }] : [{
      service: [],
      text: formatMessage({ id: 'active' }),
      // action: handleClick,
    }, {
      service: [],
      text: formatMessage({ id: 'delete' }),
      // action: confirmDelete,
    }];
    return synchronize || !active ? (<Action data={actionData} />) : null;
  }

  return (
    <div>
      <h2>{name}</h2>
      <Table
        dataSet={groupDs}
        border={false}
        queryBar="none"
      >
        <Column name="name" renderer={renderName} />
        <Column renderer={renderActions} width="0.7rem" />
        <Column name="description" />
        <Column name="clusterName" />
      </Table>
      <Modals />
    </div>
  );
});

export default Group;
