import React, { createContext, useContext, useEffect, useMemo } from 'react';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import { DataSet } from 'choerodon-ui/pro';
import ListDataSet from './ListDataSet';
import PipelineDataSet from './PipelineDataSet';
import DetailDataSet from './DetailDataSet';
import useStore from './useStore';
import usePipelineStore from './usePipelineStore';

const Store = createContext();

export function useDeployStore() {
  return useContext(Store);
}

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: { currentMenuType: { projectId } },
      intl: { formatMessage },
      children,
    } = props;
    const intlPrefix = 'c7ncd.deploy';
    const listDs = useMemo(() => new DataSet(ListDataSet(intlPrefix, formatMessage, projectId)), [projectId]);
    const pipelineDs = useMemo(() => new DataSet(PipelineDataSet(intlPrefix, formatMessage, projectId)), [projectId]);
    const detailDs = useMemo(() => new DataSet(DetailDataSet()), []);

    const deployStore = useStore();
    const pipelineStore = usePipelineStore();

    const value = {
      ...props,
      prefixCls: 'c7ncd-deploy',
      intlPrefix,
      listDs,
      pipelineDs,
      detailDs,
      deployStore,
      pipelineStore,
    };
    return (
      <Store.Provider value={value}>
        {children}
      </Store.Provider>
    );
  },
));