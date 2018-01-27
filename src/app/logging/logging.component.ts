import {Component, OnInit} from '@angular/core';
import {RobotsService} from '../robots/robots.service';

@Component({
  selector: 'app-logging',
  templateUrl: './logging.component.html',
  styleUrls: ['./logging.component.css']
})
export class LoggingComponent implements OnInit {
  private logs = [];

  constructor(private robotsService: RobotsService) {
  }

  ngOnInit(): void {
    const that = this;
    this.robotsService.initializeWebSocketConnection('/log', function (message) {
      that.pushLogs(message);
    });
  }

  private pushLogs(message) {
    if (message.body) {
      if (this.logs.length > 300) {
        this.logs.splice(0, 30);
      }
      this.logs.push(message.body);
      setTimeout(() => {
        const elem = document.getElementById('log');
        elem.scrollTop = elem.scrollHeight;
      }, 0);
    }
  }
}


