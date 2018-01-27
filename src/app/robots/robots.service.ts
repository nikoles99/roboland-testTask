import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import {Observable} from "rxjs/Observable";

@Injectable()
export class RobotsService {

  private serverUrl = 'http://localhost:8080/';
  private stompClient;

  constructor(private http: HttpClient) {
  }

  getRobots(): Observable<any> {
    return this.http.post(this.serverUrl + 'getRobots', {});
  }

  initializeWebSocketConnection(path: string, callback) {
    const ws = new SockJS(this.serverUrl + 'socket');
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe(path, (callback));
    });
  }

  addTaskToAllRobots(task) {
    return this.http.post(this.serverUrl + 'addTaskToAll', {task: task}).subscribe();
  }

  addTask(task, robot) {
    return this.http.post(this.serverUrl + 'addTask', {task: task, robotName: robot}).subscribe();
  }
}
