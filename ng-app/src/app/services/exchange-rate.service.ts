import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { ExchangeResponse } from '../models/ExchangeResponse';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class ExchangeRateService {

  constructor(private http: HttpClient, private authService: AuthServiceService) { }


  convert(baseCurrency: string, exchangeCurrenty: string, amount: number): Observable<ExchangeResponse> {
    return this.http.post<ExchangeResponse>(`api/system-development/v1/exchangeRate/convert`, {
      "from": baseCurrency.toUpperCase(),
      "to": exchangeCurrenty.toUpperCase(),
      "amount": amount
    }, {
      headers: {
        "Authorization": this.authService.getHeaderRequest()
      }
    });
  }

}
