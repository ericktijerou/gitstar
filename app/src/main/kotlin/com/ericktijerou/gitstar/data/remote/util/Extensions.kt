package com.ericktijerou.gitstar.data.remote.util

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await

suspend fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.suspendQuery(query: Query<D, T, V>): Response<T> =
    query(query).await()