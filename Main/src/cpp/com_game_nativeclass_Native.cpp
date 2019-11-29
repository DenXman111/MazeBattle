#include <bits/stdc++.h>
#include <jni.h>
#include "com_game_nativeclass_Native.h"

using namespace std;
vector < bool > f;
vector < int > dist;
set < int > s;
int distToFinish, portals;


void dfs(jint *map, int v, int N){
    if (f[v] || map[v] == 1) return;
    f[v] = 1;
    if (v >= N) dfs(map, v - N, N);
    if (v + N < N * N) dfs(map, v + N, N);
    if (v % N - 1 >= 0) dfs(map, v - 1, N);
    if (v % N + 1 < N) dfs(map, v + 1, N);
}


void dfs1(jint *map, int v, int N, int d){
    if (dist[v] <= d || map[v] == 1) return;
    dist[v] = d;
    if (map[v] == 2) s.insert(v);
    if (map[v] == 3) distToFinish = d;
    if (map[v] == 2 || map[v] == 3) return;

    if (v >= N) dfs1(map, v - N, N, d + 1);
    if (v + N < N * N) dfs1(map, v + N, N, d + 1);
    if (v % N - 1 >= 0) dfs1(map, v - 1, N, d + 1);
    if (v % N + 1 < N) dfs1(map, v + 1, N, d + 1);
}


JNIEXPORT jint JNICALL Java_com_game_nativeclass_Native_getTable (JNIEnv * env, jobject obj, jintArray table, int N, int portalN, int oldComp, int oldDistToFinish, int oldPortals, int x, int y){

        int comp;
        jint *prev = env -> GetIntArrayElements(table, NULL);
        jint map[N * N + 3];
        int ones[N * N];
        srand(time(0));

        while (1){
            s.clear();
            f.resize(N * N);
            dist.resize(N * N);
            for (int i = 0; i < N * N; i++) ones[i] = 0;
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                     int ind = i * N + j;
                    f[ind] = false;
                    dist[ind] = 1123456;
                    if (rand() % 7 < 4) map[ind] = 1; else map[ind] = 0;

                    if (map[ind] == 1){
                        bool flag = false;
                        if (ind - N >= 0) if (ones[ind - N] == 3) flag = true;
                        if (ind + N < N * N) if (ones[ind + N] == 3) flag = true;
                        if (ind % N - 1 >= 0) if (ones[ind - 1] == 3) flag = true;
                        if (ind % N + 1 < N) if (ones[ind + 1] == 3) flag = true;
                        if (flag) map[ind] = 0; else {
                            if (ind - N >= 0) ++ones[ind - N];
                            if (ind + N < N * N) ++ones[ind + N];
                            if (ind % N - 1 >= 0) ++ones[ind - 1];
                            if (ind % N + 1 < N) ++ones[ind + 1];
                        }
                    }
                }
            for (int i = 0; i < portalN; i++) map[rand() % N * N + rand() % N] = 2;

            map[rand() % N * N + rand() % N] = 3;

            if (x == -1) {
                x = rand() % N, y = rand() % N;
                while (map[x * N + y] != 0) {
                    x = rand() % N;
                    y = rand() % N;
                }
            }
            map[x * N + y] = 4;

            //GET PARAMETERS FOR GENERATED MAP

            comp = 0;
            distToFinish = N * N;
            portals = 0;

            for (int i = 0; i < N * N; i++) if (!f[i] && map[i] != 1) {
                dfs(map, i, N);
                ++comp;
            }

            distToFinish = 1123456;
            dfs1(map, x * N + y, N, 0);
            portals = s.size();
            cout << comp << " " << distToFinish << " " << portals << " : " << oldComp << endl;

            //COMPARE WITH PREVIOUS MAP BY PARAMETERS

            if (oldComp > 0) {
                if (oldComp < comp) continue;
                if (oldComp == comp){
                    if (distToFinish + 5 >= oldDistToFinish) continue;
                    if (portals == 0) continue;
                }
                if (portals == 0) continue;
                if (distToFinish < N * N || portals) break;
             } else if (distToFinish >= (N * N) && portals) break;
        }
        map[N * N] = comp;
        map[N * N + 1] = distToFinish;
        map[N * N + 2] = portals;

        env -> SetIntArrayRegion(table, 0, N * N + 3, map);
        return 0;
}