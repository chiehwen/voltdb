/* This file is part of VoltDB.
 * Copyright (C) 2008-2013 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.voltdb.iv2;

import java.util.HashSet;

import org.voltcore.messaging.TransactionInfoBaseMessage;
import org.voltdb.StoredProcedureInvocation;
import org.voltdb.dtxn.TransactionState;
import org.voltdb.messaging.Iv2InitiateTaskMessage;

public class SpTransactionState extends TransactionState
{
    final Iv2InitiateTaskMessage m_task;
    protected SpTransactionState(TransactionInfoBaseMessage notice)
    {
        super(null, notice);
        if (notice instanceof Iv2InitiateTaskMessage) {
            m_task = (Iv2InitiateTaskMessage)notice;
        } else {
            m_task = null;
        }
    }

    @Override
    public boolean isSinglePartition()
    {
        return true;
    }

    @Override
    public boolean isCoordinator()
    {
        return true;
    }

    @Override
    public boolean isBlocked()
    {
        return true;
    }

    // Per SinglePartitonTxnState.java
    @Override
    public boolean hasTransactionalWork()
    {
        return true;
    }

    @Override
    public boolean doWork(boolean recovering)
    {
        throw new RuntimeException("Do not expect doWork() in IV2");
    }

    @Override
    public StoredProcedureInvocation getInvocation()
    {
        return m_task.getStoredProcedureInvocation();
    }

    @Override
    public void handleSiteFaults(HashSet<Long> failedSites)
    {
        throw new RuntimeException("Do not expect handleSiteFaults in IV2");
    }
}
